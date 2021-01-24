package com.trustly.service;

import com.trust.comunication.GitHubRepositoryRequest;
import com.trustly.model.GitHubRepository;

public interface ApiService {
	
	
	public GitHubRepository getInfo(GitHubRepositoryRequest request);
	
	public boolean verifyGitHubUrl(GitHubRepositoryRequest request);

}
