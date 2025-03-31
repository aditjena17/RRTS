package com.RRTS.RRTS.classes;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResourcesRepository extends MongoRepository<Resources, String>{
	public Resources findByCity(String city);
}
