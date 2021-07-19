package com.example.mentor.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.mentor.model.UserBoard;

public interface UserBoardRepository extends JpaRepository<UserBoard, Long>{
}