package com.example.bookstorebe.models.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id")
    @JsonProperty("genreId")
    private int genreId;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(name = "book_genres_genre",
            joinColumns = @JoinColumn(name = "genre_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> books;

    public Genre() {}

//    public int getId() {
//        return genreId;
//    }

    public String getName() {
        return name;
    }

}
