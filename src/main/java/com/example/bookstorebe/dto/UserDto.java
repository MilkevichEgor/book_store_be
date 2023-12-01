package com.example.bookstorebe.dto;

import com.example.bookstorebe.constant.UserRole;
import com.example.bookstorebe.models.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serial;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Represents a user data transfer object.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements UserDetails {

  @Serial
  private static final long serialVersionUID = 1L;
  private Long id;
  private String name;
  private String email;
  @JsonIgnore
  private String password;
  private String avatar;
  private UserRole role;
  private List<RatingDto> ratings;
  private List<BookDto> favorites;

  /**
   * Constructor.
   */
  public UserDto(Long id, String name, String email, String password, UserRole roleName) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.password = password;
    this.role = roleName;
  }

  /**
   * Constructor.
   */
  public UserDto(String email, String password) {
    this.email = email;
    this.password = password;
  }

  /**
   * Builds a UserDetailsImpl object from a User object.
   *
   * @param user The User object to build UserDetailsImpl from.
   * @return The built UserDetailsImpl object.
   */
  public static UserDto build(User user) {

    return new UserDto(user.getUserId(),
            user.getName(),
            user.getEmail(),
            user.getPassword(),
            user.getRole());
  }

  @JsonIgnore
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @JsonIgnore
  @Override
  public String getUsername() {
    return name;
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return true;
    }
    UserDto user = (UserDto) o;
    return Objects.equals(id, user.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
