package com.example.bookstorebe.service;

import com.example.bookstorebe.service.impl.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class BookRestControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private BookService bookService;
}
