package com.gu.twitter.model;

import java.io.Serializable;

public class Contributor implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	String name;
	String description;
	String twitterUsername;
	
	
	public Contributor(String name, String description, String twitterUsername) {		
		this.name = name;
		this.description = description;
		this.twitterUsername = twitterUsername;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTwitterUsername() {
		return twitterUsername;
	}
	public void setTwitterUsername(String twitterUsername) {
		this.twitterUsername = twitterUsername;
	}
	
	

}
