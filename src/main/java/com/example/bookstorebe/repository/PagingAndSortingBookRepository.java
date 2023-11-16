//package com.example.bookstorebe.repository;
//
//import com.example.bookstorebe.entity.Book;
//import com.example.bookstorebe.entity.Genre;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.repository.PagingAndSortingRepository;
//
//public interface PagingAndSortingBookRepository extends PagingAndSortingRepository<Book, Integer> {
//
//    Iterable<Book> findAll(Sort sort);
//
//    Page<Book> findAll(Pageable pageable);
//
//    Page<Book> findByTitleContaining(String title, Pageable paging);
//
////    Page<Book> findByGenreContaining(String genre, Pageable paging);
//}
