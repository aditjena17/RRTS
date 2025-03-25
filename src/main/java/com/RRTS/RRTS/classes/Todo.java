package com.RRTS.RRTS.classes;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Document(collection = "todos")
public class Todo {
	@Id
    private int id;

    private String title; 
    private String location;
    
    private String city;

    private String description;

    private String issueType;

    private String severity;
    
    private String status;
    
    private LocalDate issueDate;
    
    private LocalDate completionDate;

    private List<String> images;
    
    @DBRef
    private User user;
    
    public enum Severity{
    	LOW,
    	MEDIUM,
    	HIGH
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIssueType() {
		return issueType;
	}

	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	public Todo(int id, String title, String location, String description, String issueType, String severity, String status,
			List<String> images, User user) {
		super();
		this.id = id;
		this.title = title;
		this.location = location;
		this.description = description;
		this.issueType = issueType;
		this.severity = severity;
		this.status = status;
		this.images = images;
		this.user = user;
	}	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Todo() {
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public LocalDate getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(LocalDate issueDate) {
		this.issueDate = issueDate;
	}

	public LocalDate getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(LocalDate completionDate) {
		this.completionDate = completionDate;
	}	
}
