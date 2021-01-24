package com.trustly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trust.comunication.GitHubRepositoryRequest;
import com.trustly.model.FileType;
import com.trustly.model.GitHubRepository;
import com.trustly.model.Page;
import com.trustly.service.ApiService;
import com.trustly.service.CrawlerService;

@Service
public class ApiServiceImpl implements ApiService {
	
	@Autowired
	private CrawlerService crawlerService;
	

	@Override
	public GitHubRepository getInfo(GitHubRepositoryRequest request) {
		GitHubRepository repo = new GitHubRepository(request.getUrl());
		
		String splitted[] = request.getUrl().split("/");
		
		
		repo.setName(splitted[4]);
		repo.setCreator(splitted[3]);
		
		getPages(request.getUrl()).stream().forEach(p -> {
			
			Long newNumberOfFiles = repo.getNumberOfFiles() + 1;
			repo.setNumberOfFiles(newNumberOfFiles);
			Float newRepoSize = repo.getRepositorySize() + p.getSize();
			repo.setRepositorySize(newRepoSize);
			Long newNumberOfLines = repo.getNumberOfLines() + p.getNumberOfLines();
			repo.setNumberOfLines(newNumberOfLines);
			
			
			
			repo.getFiles()
			.stream()
			.filter(f->f.getType().equals(p.getType()))
			.findAny()
			.ifPresentOrElse(
					   file -> 
					   file.setTotalSize(file.getTotalSize() + p.getSize())
					   ,
					   () -> 
					   repo.getFiles().add(new FileType(p.getType(), p.getSize()))
					);
			
		});
		
		return repo;
	}
	
	private List<Page> getPages(String url) {		
		return crawlerService.search(url);
		
	}

}
