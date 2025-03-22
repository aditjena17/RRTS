package com.RRTS.RRTS.classes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourcesRepository extends JpaRepository<Resources, String>{
	public List<Resources> findByCity(String city);
}
