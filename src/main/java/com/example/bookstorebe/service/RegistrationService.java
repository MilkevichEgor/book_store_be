package com.example.bookstorebe.service;

import com.example.bookstorebe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class for registering new users.
 */
@Service
public class RegistrationService {

  private final UserRepository userRepository;
  private final PasswordEncoder encoder;

  @Autowired
  public RegistrationService(UserRepository userRepository,
                             PasswordEncoder encoder) {
    this.userRepository = userRepository;
    this.encoder = encoder;
  }

}
