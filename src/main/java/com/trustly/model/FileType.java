package com.trustly.model;

public class FileType {
	
	String type;
	Float totalSize = 0f;
		
	public FileType() {
		super();
	}
	
		
	public FileType(String type, Float totalSize) {
		super();
		this.type = type;
		this.totalSize = totalSize;
	}



	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public Float getTotalSize() {
		return totalSize;
	}
	
	public void setTotalSize(Float totalSize) {
		this.totalSize = totalSize;
	}
	
	

}
