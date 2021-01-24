package com.trustly.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;import java.util.stream.Collector;

import org.springframework.stereotype.Service;

import com.trustly.model.Page;
import com.trustly.service.CrawlerService;


@Service
public class CrawlerServiceImpl implements CrawlerService {
	
	private static final Integer KB = 1024;
	private static final Integer MB = 1024 * KB;
	private static final Integer GB = 1024 * MB;
	private static final String GITHUB_BYTES = "Bytes";
	private static final String GITHUB_KB = "KB";
	private static final String GITHUB_MB = "MB";
	private static final String GITHUB_GB = "GB";
	private static final String GITHUB_PREFIX = "https://github.com";
	
	@Override
	public List<Page> search(String url) {
		
		String webPageString = getWebPageString(url);
		
		List<String> linksQueue  = getQueue(webPageString);
		
		List<Page> pages = new ArrayList<>();
		

		linksQueue.parallelStream().forEach(l ->{ 
			pages.add(getPageInfo(l));
		});
		
		
	
		return pages;
	}
	
	
	
	private Page getPageInfo(String pageUrl) {
		
		Page page = new Page();
		
		page.setUrl(pageUrl);
		
		String url = pageUrl;
		String splitted[] = url.split("/");
		String fileType = splitted[splitted.length - 1];
		if (fileType.contains(".")) {
			page.setType(fileType.split("\\.")[1]);
		}else{
			page.setType(fileType);
		}

		
		
		String webPageString = getWebPageString(page.getUrl());
		
		Pattern pattern = Pattern.compile("<divclass=\"text-monof6flex-autopr-3flex-order-2flex-md-order-1mt-2mt-md-0\"\\>(.+?)\\<\\/div\\>");
		Matcher matcher = pattern.matcher(webPageString);
		if (matcher.find()) {
			
			String htmlContent = matcher.group(1);
			
			if (htmlContent.contains("line")) {
				splitted = htmlContent.split("line");
				splitted[0] = splitted[0].replace("<spanclass=\"file-mode\"title=\"Filemode\">executablefile</span><spanclass=\"file-info-divider\"></span>", "");
				page.setNumberOfLines(Long.parseLong(splitted[0]));
				
				splitted = htmlContent.split("</span>");
				String size = splitted[1].replace("</div>", "");					
						
				if (size.contains(GITHUB_BYTES)) {
					size = size.split(GITHUB_BYTES)[0];	
					page.setSize(Float.parseFloat(size));
					
				} else if (size.contains(GITHUB_KB)) {
					size = size.split(GITHUB_KB)[0];	
					Float fSize = Float.parseFloat(size);
					page.setSize(fSize * KB);
					
				} else if (size.contains(GITHUB_MB)) {
					size = size.split(GITHUB_MB)[0];						
					Float fSize = Float.parseFloat(size);
					page.setSize(fSize * MB);
					
				} else if (size.contains(GITHUB_GB)) {
					size = size.split(GITHUB_GB)[0];						
					Float fSize = Float.parseFloat(size);
					page.setSize(fSize * GB);
					
				}
				
				
			} else {
				
				if (htmlContent.contains(GITHUB_BYTES)) {
					String size = htmlContent.split(GITHUB_BYTES)[0];	
					page.setSize(Float.parseFloat(size));
					
				} else if (htmlContent.contains(GITHUB_KB)) {
					String size = htmlContent.split(GITHUB_KB)[0];	
					Float fSize = Float.parseFloat(size);
					page.setSize(fSize * KB);
					
				} else if (htmlContent.contains(GITHUB_MB)) {
					String size = htmlContent.split(GITHUB_MB)[0];						
					Float fSize = Float.parseFloat(size);
					page.setSize(fSize * MB);
					
				} else if (htmlContent.contains(GITHUB_GB)) {
					String size = htmlContent.split(GITHUB_GB)[0];						
					Float fSize = Float.parseFloat(size);
					page.setSize(fSize * GB);
					
				}
				
				page.setNumberOfLines(0l);
			}
			
			
		}
		
		return page;
		
		
	}
	
	private List<String> getQueue(String webPageString) {
		
		List<String> queue = new ArrayList<>();
		List<String> tree = new ArrayList<>();
		
		Pattern pattern = Pattern.compile("class=\"js-navigation-openlink-gray-dark\"[^>]*href=\"([^\"]*)\"[^>]*|href=\"([^\"]*)\"[^>]*class=\"js-navigation-openlink-gray-dark\"");
		Matcher matcher = pattern.matcher(webPageString);
		while (matcher.find()) {
			if (matcher.group().contains("tree"))  {
				String treeUrl = GITHUB_PREFIX + matcher.group(1);
				tree.add(treeUrl);						
			} else if (matcher.group().contains("blob")) {
				queue.add(GITHUB_PREFIX + matcher.group(1));	
			}

		} 
		
		tree.parallelStream().forEach(s -> {
			String htmlString = getWebPageString(s);
			queue.addAll(getQueue(htmlString));
		});
		
		
		return queue;
		
	}
	
	
	private String getWebPageString(String requestUrl) {
		
		try {
			URL url = new URL(requestUrl);			
			StringBuffer buffer = new StringBuffer();

			Scanner scanner = new Scanner(url.openStream());
			
			while (scanner.hasNext()) {
				buffer.append(scanner.next());
			}
			return buffer.toString();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return null;
	   
	      
	}

}
