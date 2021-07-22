package com.example.mentor.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.board.model.TestBoard;
import com.project.board.model.TestBoard2;

public interface BoardRepository2 extends JpaRepository<TestBoard2, Long>{
	List<TestBoard2> findByTitleContaining(String keyword);
}