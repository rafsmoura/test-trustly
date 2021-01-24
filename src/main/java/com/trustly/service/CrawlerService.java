package com.trustly.service;

import java.util.List;

import com.trustly.model.Page;

public interface CrawlerService {
	
	
	public List<Page> search(String url);
		

}
