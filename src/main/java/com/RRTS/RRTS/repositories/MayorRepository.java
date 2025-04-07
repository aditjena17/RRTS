package com.RRTS.RRTS.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.RRTS.RRTS.classes.User;
import com.RRTS.RRTS.roles.Mayor;

public interface MayorRepository extends MongoRepository<Mayor, Integer>{

	public void deleteByEmail(String email);

}
