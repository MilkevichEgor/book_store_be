package com.example.bookstorebe.dto.web;

import com.example.bookstorebe.constant.UserRole;
import com.example.bookstorebe.dto.RatingDto;
import com.example.bookstorebe.dto.UserDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWebDto {
  private Long id;
  private UserRole role;
  private String name;
  private String email;
  private String avatar;
  private List<RatingDto> ratings;
  private List<BookWebDto> favorites;

  public UserWebDto(UserDto dto) {
    this.id = dto.getId();
    this.role = dto.getRole();
    this.name = dto.getName();
    this.email = dto.getEmail();
    this.avatar = dto.getAvatar();
    this.ratings = dto.getRatings();
    this.favorites = dto.getFavorites().stream().map(BookWebDto::new).toList();
  }
}
