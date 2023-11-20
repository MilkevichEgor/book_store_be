package com.example.bookstorebe.models.entity;

import com.example.bookstorebe.DTO.BookResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    @JsonProperty("bookId")
    private int bookId;

    @Transient
    @JsonIgnore
    private int id;

    @Column(length = 255, nullable = false)
    private String title;

    @Column(length = 255, nullable = false)
    private String author;

    @Column(nullable = false)
    private int price;

    @Column(nullable = true)
    private String cover;

    @Column(name="dateOfIssue",nullable = true)
    private Date dateOfIssue;

    @Column(name="inStock",nullable = true)
    private int inStock;

    @Column(nullable = true)
    private String description;

    @Column(name="averageRate",nullable = true)
    private float averageRate;

    @Transient
    @JsonProperty("isInFavorite")
    private boolean isInFavorite;


    public void setInitialIsInFavoritesValue(boolean b) {
        this.isInFavorite = b;
    }

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "book_genres_genre",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genres;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "book_users_id",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

    @OneToMany(mappedBy = "book")
    @JsonManagedReference
    private List<Comment> comments;

    @OneToMany(mappedBy = "book")
    private List<Rating> ratings;

    public BookResponse toResponse(Boolean isInFavorites) {
        BookResponse bookResponse = new BookResponse();
        bookResponse.setBookId(this.bookId);
        bookResponse.setTitle(this.title);
        bookResponse.setAuthor(this.author);
        bookResponse.setPrice(this.price);
        bookResponse.setCover(this.cover);
        bookResponse.setDateOfIssue(this.dateOfIssue);
        bookResponse.setInStock(this.inStock);
        bookResponse.setDescription(this.description);
        bookResponse.setAverageRate(this.averageRate);
        bookResponse.setRatings(this.ratings);
        bookResponse.setComments(this.comments);
        bookResponse.setIsInFavorite(isInFavorites);

        return bookResponse;
    }
    public void setUser(User user) {
        this.users.add(user);
    }

//    public void addComment(Comment comment) {
//        this.comments.add(comment);
//    }

    public Boolean isInFavorite() {
//        return isInFavorite;
        return !this.getUsers().isEmpty();
    }

}
