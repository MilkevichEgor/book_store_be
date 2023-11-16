package com.example.bookstorebe.security;

import com.example.bookstorebe.models.entity.Book;
import com.example.bookstorebe.models.entity.Rating;
import com.example.bookstorebe.models.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String email;

    @JsonIgnore
    private String password;
    private String avatar;
    private List<Rating> ratings;
    private List<Book> favorites;

    @JsonIgnore
    private Collection<? extends GrantedAuthority> authorities;

    @Getter
    private User.UserRole role;

    public UserDetailsImpl(Integer id, String name, String email, String password,
                           User.UserRole roleName) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = roleName;
    }

    public static UserDetailsImpl build(User user) {

        return new UserDetailsImpl(user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getRole());
    }

    public User getUser() {
        return new User(id, name, email, password, role);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
