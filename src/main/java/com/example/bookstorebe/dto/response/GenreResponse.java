package com.example.bookstorebe.dto.response;

import com.example.bookstorebe.dto.GenreDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenreResponse {
  private List<GenreDto> genres;
}
