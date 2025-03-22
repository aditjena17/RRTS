package com.RRTS.RRTS.classes;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>{
	public User findByEmail(String email);
	public boolean existsByEmail(String email);
}
