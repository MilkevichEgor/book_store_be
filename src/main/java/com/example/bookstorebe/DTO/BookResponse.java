package com.example.bookstorebe.DTO;

import com.example.bookstorebe.models.entity.Book;
import com.example.bookstorebe.models.entity.Comment;
import com.example.bookstorebe.models.entity.Rating;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;


public class BookResponse {

    @Transient
    @Getter
    @Setter
    private Integer bookId;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String author;

    @Getter
    @Setter
    private Integer price;

    @Getter
    @Setter
    private String cover;

    @Getter
    @Setter
    private String text;

    @Getter
    @Setter
    private Date dateOfIssue;

    @Getter
    @Setter
    private int inStock;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private Float averageRate;

    @Getter
    @Setter
    private List<Rating> ratings;

    @Getter
    @Setter
    private List<Comment> comments;

    @Getter
    @Setter
    @JsonProperty("isInFavorite")
    private Boolean isInFavorite;

    public BookResponse() {}

    public BookResponse(Book book) {
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.price = book.getPrice();
        this.cover = book.getCover();
        this.dateOfIssue = book.getDateOfIssue();
        this.inStock = book.getInStock();
        this.description = book.getDescription();
        this.averageRate = book.getAverageRate();
//        this.ratings = book.getRatings();
//        this.comments = book.getComments();
        this.isInFavorite = book.isInFavorite();
    }


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

    public void setIsInFavorite(Boolean isInFavorite) {
        this.isInFavorite = isInFavorite;
    }

    public String getText() {
        return text;
    }

}


