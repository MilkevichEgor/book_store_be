package com.example.bookstorebe.dto.response;

import java.util.Map;

public class OneFieldResponse<T> {
  public static <T> Map<String, T> of(String key, T value) {
    return Map.of(key, value);
  }
}
