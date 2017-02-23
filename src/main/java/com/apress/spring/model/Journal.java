package com.apress.spring.model;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author zshatzov
 *
 */
@Entity
@Table(uniqueConstraints={
	@UniqueConstraint(name="unique_journal_title", columnNames="title")	
})
@XmlRootElement(name="journal")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Journal implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false, unique=true)
	@NotNull
	private String title; 

	private final Instant created;
	
	private String summary;

	public Journal() {
		created = Instant.now();
	}

	public Journal(String title, String summary) {
		this();
		this.title = title;
		this.summary = summary;	
	}
	
	@JsonIgnore
	public String getCreatedFormatted(){
		return LocalDateTime.ofInstant(created, ZoneId.systemDefault())
					.format(DateTimeFormatter.ISO_DATE_TIME);
	}

	public Long getId() {
		return id;
	}

	private void setId(Long id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getSummary() {
		return summary;
	}


	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	@JsonIgnore
	public Instant getCreated() {
		return created;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Journal [title=");
		builder.append(title);
		builder.append(", created=");
		builder.append(getCreatedFormatted());
		builder.append(", summary=");
		builder.append(summary);
		builder.append("]");
		return builder.toString();
	}
}
