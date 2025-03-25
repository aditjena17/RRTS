package com.RRTS.RRTS.classes;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Integer>{
	public User findByEmail(String email);
	public boolean existsByEmail(String email);
}
