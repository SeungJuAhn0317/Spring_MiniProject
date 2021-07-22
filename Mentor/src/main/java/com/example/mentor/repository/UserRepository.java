package com.example.mentor.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mentor.model.TestUser;

public interface UserRepository extends JpaRepository<TestUser, Long> {
	public TestUser findByEmailAndPwd(String email, String pwd);
}
