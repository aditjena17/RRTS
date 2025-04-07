package com.RRTS.RRTS.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.RRTS.RRTS.classes.User;
import com.RRTS.RRTS.roles.CityAdmin;

public interface CityadminRepository extends MongoRepository<CityAdmin, Integer>{

	public void deleteByEmail(String email);

}
