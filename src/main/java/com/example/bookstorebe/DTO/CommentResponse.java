package com.example.bookstorebe.DTO;

import com.example.bookstorebe.models.entity.Book;
import com.example.bookstorebe.models.entity.User;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class CommentResponse {

    @Transient
    @Getter
    @Setter
    private Integer commentId;

    @Getter
    @Setter
    private String text;

    @Getter
    @Setter
    private Date date;

    @Getter
    @Setter
    private User user;

    @Getter
    @Setter
    private Book book;

    public CommentResponse() {

    }

    public CommentResponse(Date date, String text) {
        this.date = date;
        this.text = text;
    }
}
