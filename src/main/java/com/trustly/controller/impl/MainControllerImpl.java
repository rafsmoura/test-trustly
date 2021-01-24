package com.trustly.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trustly.comunication.GitHubRepositoryRequest;
import com.trustly.controller.MainController;
import com.trustly.model.GitHubRepository;
import com.trustly.service.ApiService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/trustly")
@Api(value = "API for get data by scraping GitHub repositories pages")
@CrossOrigin("*")
public class MainControllerImpl implements MainController {
	
	@Autowired
	private ApiService apiService;

	
	@PostMapping(path="/get-info", consumes = "application/json", produces = "application/json")
	@ApiOperation(value = "Return the total size (in bytes) of requested repository, number of lines and files.")
	@Override
	public ResponseEntity<GitHubRepository> getGitHubRepositoryInfo(@RequestBody GitHubRepositoryRequest request) {
		
		return ResponseEntity.ok(apiService.getInfo(request));
	}

}
