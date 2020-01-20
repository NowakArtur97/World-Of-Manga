package com.NowakArtur97.WorldOfManga.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NowakArtur97.WorldOfManga.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByUserName(String userName);
}
