package com.trustly.model;

import java.util.ArrayList;
import java.util.List;

public class GitHubRepository {
	
	String name;
	String url;
	Float repositorySize = 0f;
	String creator;
	Long numberOfFiles = 0l;
	Long numberOfLines = 0l;
	List<FileType> files = new ArrayList<>();
			
	public GitHubRepository(String url) {
		super();
		this.url = url;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Float getRepositorySize() {
		return repositorySize;
	}
	public void setRepositorySize(Float repositorySize) {
		this.repositorySize = repositorySize;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Long getNumberOfFiles() {
		return numberOfFiles;
	}
	public void setNumberOfFiles(Long numberOfFiles) {
		this.numberOfFiles = numberOfFiles;
	}
	public Long getNumberOfLines() {
		return numberOfLines;
	}
	public void setNumberOfLines(Long numberOfLines) {
		this.numberOfLines = numberOfLines;
	}
	public List<FileType> getFiles() {
		return files;
	}
	public void setFiles(List<FileType> files) {
		this.files = files;
	}
	
	
	
	

}
