package com.example.mentor.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.board.model.TestBoard;

public interface BoardRepository extends JpaRepository<TestBoard, Long>{
	List<TestBoard> findByTitleContaining(String keyword);
}