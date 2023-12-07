package com.example.bookstorebe;

import com.example.bookstorebe.service.FilesStorageService;
import com.example.bookstorebe.socket.SocketEmitter;
import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot application.
 */
@SpringBootApplication
public class BookStoreBeApplication implements CommandLineRunner {

  @Resource
  FilesStorageService storageService;

  @Resource
  SocketEmitter socketEmitter;

  /**
   * Main method.
   *
   * @param args The command line arguments
   */
  public static void main(String[] args) {
    SpringApplication application = new SpringApplication(BookStoreBeApplication.class);
    application.setAdditionalProfiles("dev");
    application.run(args);


  }

  @Override
  public void run(String... arg) throws Exception {
    socketEmitter.socketStart();
    storageService.init();
  }

}

