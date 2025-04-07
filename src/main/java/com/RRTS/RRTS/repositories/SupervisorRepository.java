package com.RRTS.RRTS.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.RRTS.RRTS.classes.User;
import com.RRTS.RRTS.roles.Supervisor;

public interface SupervisorRepository extends MongoRepository<Supervisor, Integer>{

	public void deleteByEmail(String email);

}
