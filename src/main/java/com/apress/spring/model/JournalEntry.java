package com.apress.spring.model;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class JournalEntry implements Serializable {
		 
	private static final long serialVersionUID = 1L;
	
	private final String title;
	@JsonIgnore
	private Instant created;
	private String summary;

	JournalEntry() {		
		this("Some title");
	}

	public JournalEntry(String title) {
		this.title = title;
		this.created = Instant.now(); 	
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public void setCreated(long epochSec){
		this.created = Instant.ofEpochSecond(epochSec);
	}
	
	public String getTitle() {
		return title;
	}

	public Instant getCreated() {
		return created;
	}

	public String getCreatedFormatted(){
		return LocalDateTime.ofInstant(created, 
				ZoneId.systemDefault()).format(DateTimeFormatter.ISO_DATE_TIME);
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JournalEntry [title=");
		builder.append(title);
		builder.append(", created=");
		builder.append(getCreatedFormatted());
		builder.append("]");
		return builder.toString();
	}
}
