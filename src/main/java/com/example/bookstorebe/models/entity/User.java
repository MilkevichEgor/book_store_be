package com.example.bookstorebe.models.entity;

import com.example.bookstorebe.constant.UserRole;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Entity user.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
@Entity
@ToString(exclude = {"comments", "ratings", "favorites"})
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "user_id")
  private Long userId;

  @Column(columnDefinition = "VARCHAR(255) DEFAULT 'USER'")
  @Enumerated(EnumType.STRING)
  private UserRole role;

  private String name;

  @Column(length = 255, unique = true, nullable = false)
  private String email;

  @JsonIgnore
  private String password;

  private String avatar;

  @OneToMany(mappedBy = "user")
  private List<Rating> ratings;

  @OneToMany(mappedBy = "user")
  private List<Comment> comments;

  @ManyToMany(cascade = {CascadeType.ALL})
  @JoinTable(name = "book_users_id",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "book_id"))
  private Set<Book> favorites;

  public User(Long id, String name, String email, String password, UserRole role) {
  }

  /**
   * Compare two users by id.
   *
   * @param o The other user.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    User user = (User) o;
    return userId.equals(user.userId);
  }

  /**
   * Returns the hash code value for this object.
   *
   * @return the hash code value for this object
   */
  @Override
  public int hashCode() {
    return Objects.hash(userId);
  }

}
