package com.trustly.service.impl;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.trustly.comunication.GitHubRepositoryRequest;
import com.trustly.model.FileType;
import com.trustly.model.GitHubRepository;
import com.trustly.model.HttpError;
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
		
		if (!verifyGitHubUrl(request)) {
			HttpError error = new HttpError();
			error.setCode(404);
			error.setMessage(request.getUrl() + " is not a valid GitHub repository URL.");
			repo.getErrors().add(error);
			return repo;
		}
		
		String splitted[] = request.getUrl().split("/");
		
		
		repo.setName(splitted[4]);
		repo.setCreator(splitted[3]);
		
		try {
		
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
		
		} catch (Exception e) {
			HttpError error = new HttpError();
			error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			error.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.toString());
			repo.getErrors().add(error);
			return repo;
		}
		
		return repo;
	}
	
	private List<Page> getPages(String url) {		
		return crawlerService.search(url);
		
	}

	@Override
	public boolean verifyGitHubUrl(GitHubRepositoryRequest request) {
		
		Pattern pattern = Pattern.compile("(http(|s)\\:\\/\\/)([www.github.com]+)(:|\\/)([A-Za-z0-9-]+)(:|\\/)([A-Za-z0-9-\\\\/]*)");
		Matcher matcher = pattern.matcher(request.getUrl());		
		if (matcher.find()) {
			return true;
		}
		return false;

	}

}
