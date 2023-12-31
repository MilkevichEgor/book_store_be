package com.example.bookstorebe.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request object for adding a book to the user's favorites.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookFavoriteRequest {
  private Long bookId;
}
