package com.example.bookstorebe.DTO;

import com.example.bookstorebe.models.entity.Book;
import com.example.bookstorebe.models.entity.Rating;
import com.example.bookstorebe.models.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class UserResponse {

    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    private User.UserRole role;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String avatar;

    @Getter
    @Setter
    private List<Rating> ratings;

    @Getter
    @Setter
    private List<Book> favorites;
//    private List<Book> favorites = new ArrayList<>();


//    public UserResponse(Integer id, User.UserRole role, String name,
//                        String email, String avatar,
//                        List<Rating> ratings, List<Book> favorites) {
//        this.id = id;
//        this.role = role;
//        this.name = name;
//        this.email = email;
//        this.avatar = avatar;
//        this.ratings = ratings;
//        this.favorites = favorites;
//
//    }

    public UserResponse(Integer id, User.UserRole role, String name,
                        String email, String avatar) {
        this.id = id;
        this.role = role;
        this.name = name;
        this.email = email;
        this.avatar = avatar;
    }

    public UserResponse() {

    }

    public void setUsername(String username) {
        this.name = username;
    }

    public void setRoles(User.UserRole role) {
        this.role = role;
    }

    public void addToFavorites(Book book) {
        favorites.add(book);
    }
}
