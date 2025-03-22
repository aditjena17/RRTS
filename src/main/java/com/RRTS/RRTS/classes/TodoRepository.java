package com.RRTS.RRTS.classes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Integer>{
	public List<Todo> findByUser(User user);
	public List<Todo> findAll(); 
}
