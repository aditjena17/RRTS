package com.RRTS.RRTS.classes;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TodoRepository extends MongoRepository<Todo, Integer>{
	public List<Todo> findByUser(User user);
	public List<Todo> findAll(); 
}
