package com.trustly.controller;

import org.springframework.http.ResponseEntity;

import com.trust.comunication.GitHubRepositoryRequest;
import com.trustly.model.GitHubRepository;

public interface MainController {
		
	public ResponseEntity<GitHubRepository> getGitHubRepositoryInfo(GitHubRepositoryRequest request);

}
