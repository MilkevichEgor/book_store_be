package com.example.bookstorebe.repository;

import com.example.bookstorebe.models.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository class for managing comments.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
