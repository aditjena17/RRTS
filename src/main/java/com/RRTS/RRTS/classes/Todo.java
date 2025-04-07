package com.RRTS.RRTS.classes;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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
    private String state;
    private String description;
    private String issueType;
    private String severity;    
    private String status;    
    private LocalDate issueDate;    
    private LocalDate completionDate;
    private List<String> images;
    private String email="";
    private int manpower;
    private String approved;
    
    private int estimated_cost;
    private List<String> materials_used;
    private String supervisor_name;
    private String supervisor_email;
    
    @Field("resources")
    private Map<String, Integer> resources = new HashMap<>();
    private int machines;
    
    @DBRef
    private User user;  
    
    public enum Severity{
    	LOW,
    	MEDIUM,
    	HIGH
    }           
    
	public int getEstimated_cost() {
		return estimated_cost;
	}
	public void setEstimated_cost(int estimated_cost) {
		this.estimated_cost = estimated_cost;
	}
	public List<String> getMaterials_used() {
		return materials_used;
	}
	public void setMaterials_used(List<String> materials_used) {
		this.materials_used = materials_used;
	}
	public String getSupervisor_name() {
		return supervisor_name;
	}
	public void setSupervisor_name(String supervisor_name) {
		this.supervisor_name = supervisor_name;
	}
	public String getSupervisor_email() {
		return supervisor_email;
	}
	public void setSupervisor_email(String supervisor_email) {
		this.supervisor_email = supervisor_email;
	}
	public String getApproved() {
		return approved;
	}
	public void setApproved(String approved) {
		this.approved = approved;
	}
	public Map<String, Integer> getResources() {
		return resources;
	}
	public void setResources(Map<String, Integer> resources) {
		this.resources = resources;
	}
	
	public void updateResource(String oldType, String newType, int quantity) {
        if (resources.containsKey(oldType)) {
            // Remove the old resource entry
            resources.remove(oldType);
            // Add the new resource type with the updated quantity
            resources.put(newType, quantity);
        }
    }
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public void setManpower(int manpower) {
		this.manpower = manpower;
	}
	public void setMachines(int machines) {
		this.machines = machines;
	}
	public int getManpower() {
		return manpower;
	}
	public int getMachines() {
		return machines;
	}
    
}
