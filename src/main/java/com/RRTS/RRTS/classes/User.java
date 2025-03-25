package com.RRTS.RRTS.classes;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.annotation.Nullable;
import lombok.Data;
@Data
@Document(collection = "users")
public class User {
	@Id
    private int id;

    private String firstName;
    private String lastName;
    
    private String email;
    
    private String phoneNumber;
    
    private String password;
    
    private String role;
    
    @DBRef
    @JsonIgnore
    private List<Todo> todos;
    
    public void setPassword(String password) {
//        this.password = new BCryptPasswordEncoder().encode(password);
    	this.password = password;
    }
    
    public String getPassword() {
    	return password;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<Todo> getTodos() {
		return todos;
	}

	public void setTodos(List<Todo> todos) {
		this.todos = todos;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", phoneNumber=" + phoneNumber + ", password=" + password + ", role=" + role + ", todos=" + todos
				+ "]";
	}

	public User(int id, String firstName, String lastName, String email, String phoneNumber, String password,
			String role, List<Todo> todos) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.role = role;
		this.todos = todos;
	}
	
	public User() {}
}
