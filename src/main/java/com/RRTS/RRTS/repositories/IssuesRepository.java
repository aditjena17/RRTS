package com.RRTS.RRTS.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.RRTS.RRTS.roles.Issues;

public interface IssuesRepository extends MongoRepository<Issues, Integer>{

	public Issues findByRequestId(String requestId);
	
}
