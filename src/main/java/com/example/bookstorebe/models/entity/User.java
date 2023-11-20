package com.example.bookstorebe.models.entity;

import com.example.bookstorebe.DTO.UserResponse;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int userId;

    @Column(columnDefinition = "VARCHAR(255) DEFAULT 'USER'")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User(Integer id, String name, String email, String password, UserRole role) {
    }
    public void setAvatar(String avatarName) {
        this.avatar = avatarName;
    }

    public enum UserRole {
        USER,
        ADMIN
    }

    @Column(name = "name", length = 255, nullable = true)
    private String username;

    @Column(length = 255 ,unique = true, nullable = false)
    private String email;

    @JsonIgnore
    @Column(nullable = true)
    private String password;

    @Column(nullable = true)
    private String avatar;

    @OneToMany(mappedBy = "user")
    private List<Rating> ratings;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "book_users_id",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> favorites;
    

    public UserResponse toResponse() {
        UserResponse userResponse = new UserResponse();
        userResponse.setEmail(this.email);
        userResponse.setRoles(this.role);
        userResponse.setId(this.userId);
        userResponse.setRatings(this.ratings);
        userResponse.setAvatar(this.avatar);
        userResponse.setName(this.username);
        userResponse.setFavorites(this.favorites);

        return userResponse;
    }

    public void addToFavorites(Book book) {
        favorites.add(book);
    }

    public void removeFromFavorites(Book book) {
        favorites.remove(book);
    }

}
