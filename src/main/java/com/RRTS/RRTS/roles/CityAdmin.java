package com.RRTS.RRTS.roles;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.RRTS.RRTS.classes.User;

import lombok.Data;

@Data
@Document(collection = "City_Admin")
public class CityAdmin {
	private String id;
    private String firstName;
    private String lastName;   
    
    @Id
    private String email;    
    private String phoneNumber;    
    private String password;    
    
    private String city;    
    private String state;    
    private String role;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public String getState() {
		return state;
	}
	public CityAdmin(){}
	
	public CityAdmin(User user) {
		this.setId(user.getId());
        this.setEmail(user.getEmail());
        this.setRole(user.getRole());
        this.setCity(user.getCity());
        this.setState(user.getState());
        this.setFirstName(user.getFirstName());
        this.setLastName(user.getLastName());
        this.setPhoneNumber(user.getPhoneNumber());
        this.setPassword(user.getPassword());
	}
}
