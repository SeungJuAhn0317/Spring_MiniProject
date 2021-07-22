package com.example.mentor.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mentor.model.TestBoard2;

public interface BoardRepository2 extends JpaRepository<TestBoard2, Long>{
	List<TestBoard2> findByTitleContaining(String keyword);
}