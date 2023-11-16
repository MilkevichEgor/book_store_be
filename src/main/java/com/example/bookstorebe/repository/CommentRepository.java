package com.example.bookstorebe.repository;

import com.example.bookstorebe.models.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
