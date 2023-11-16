package com.example.bookstorebe.repository;

import com.example.bookstorebe.models.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<Book, Integer>,
        JpaSpecificationExecutor<Book> {

    Book getBookById(Integer bookId);

    Book getById(Integer id);

    Page<Book> findAllByTitleContainingIgnoreCase(String title, Pageable pageable);

//    Page<Book> findAll(Specification<Book> specification, Pageable pageable);
}




