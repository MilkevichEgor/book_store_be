package com.example.bookstorebe.dto.response;

import java.util.Map;

/**
 * Generic class to create one field response.
 */
public class OneFieldResponse<T> {
  public static <T> Map<String, T> of(String key, T value) {
    return Map.of(key, value);
  }
}
