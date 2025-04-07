package com.RRTS.RRTS.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.RRTS.RRTS.classes.User;
import com.RRTS.RRTS.roles.Resident;

public interface ResidentRepository extends MongoRepository<Resident, Integer>{

	public void deleteByEmail(String email);

}
