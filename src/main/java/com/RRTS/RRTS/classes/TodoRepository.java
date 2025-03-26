package com.RRTS.RRTS.classes;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface TodoRepository extends MongoRepository<Todo, Integer>{
	public List<Todo> findByUser(User user);
	public List<Todo> findAll();
	public void deleteAllByUser(User user);
}
