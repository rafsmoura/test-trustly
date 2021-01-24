package com.trustly.model;



public class Page {
	
	String url;
	String type;
	Long numberOfLines = 0l;
	Float size = 0f;
		
	public Page() {
		super();
	}
		
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getNumberOfLines() {
		return numberOfLines;
	}
	public void setNumberOfLines(Long numberOfLines) {
		this.numberOfLines = numberOfLines;
	}
	public Float getSize() {
		return size;
	}
	public void setSize(Float size) {
		this.size = size;
	}
	
	
	

}
