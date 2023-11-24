package com.example.bookstorebe.dto.response;

import com.example.bookstorebe.dto.web.BookWebDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Entity book for response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {

  private BookWebDto book;

}


