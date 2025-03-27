package com.RRTS.RRTS.classes;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
@Document(collection = "Complaints")
public class Todo {
	@Id
    private String id;
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
    private String manpower="";
    private String resources="";
    private String machines="";
    
    @DBRef
    private User user;  
    
    public enum Severity{
    	LOW,
    	MEDIUM,
    	HIGH
    }
	public void setId(String id) {
		this.id = id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setIssueDate(LocalDate issueDate) {
		this.issueDate = issueDate;
	}
	public void setCompletionDate(LocalDate completionDate) {
		this.completionDate = completionDate;
	}
	public void setImages(List<String> images) {
		this.images = images;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public String getLocation() {
		return location;
	}
	public String getCity() {
		return city;
	}
	public String getDescription() {
		return description;
	}
	public String getIssueType() {
		return issueType;
	}
	public String getSeverity() {
		return severity;
	}
	public String getStatus() {
		return status;
	}
	public LocalDate getIssueDate() {
		return issueDate;
	}
	public LocalDate getCompletionDate() {
		return completionDate;
	}
	public List<String> getImages() {
		return images;
	}
	public User getUser() {
		return user;
	}
	public void setManpower(String manpower) {
		this.manpower = manpower;
	}
	public void setResources(String resources) {
		this.resources = resources;
	}
	public void setMachines(String machines) {
		this.machines = machines;
	}
	public String getManpower() {
		return manpower;
	}
	public String getResources() {
		return resources;
	}
	public String getMachines() {
		return machines;
	}
    
}
