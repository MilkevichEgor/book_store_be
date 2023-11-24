package com.example.bookstorebe.service.impl;

import com.example.bookstorebe.service.FilesStorageService;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * This class implements the FilesStorageService interface and provides methods for
 * initializing the file storage, saving files, and checking if an avatar exists.
 */
@Service
public class FilesStorageServiceImpl implements FilesStorageService {
  private final Path root = Paths.get("./uploads");

  Logger filesStorageLogger = LoggerFactory.getLogger(FilesStorageServiceImpl.class);

  @Override
  public void init() {
    try {
      Files.createDirectories(root);
    } catch (IOException e) {
      filesStorageLogger.error("Could not initialize folder for upload!", e);
    }
  }

  @Override
  public Resource load(String filename) {
    try {
      Path file = root.resolve(filename);
      Resource resource = new UrlResource(file.toUri());

      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        throw new RuntimeException("Could not read the file!");
      }
    } catch (MalformedURLException e) {
      throw new RuntimeException("Error: " + e.getMessage());
    }
  }

  @Override
  public void save(MultipartFile file) {
    try {
      Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()),
              StandardCopyOption.REPLACE_EXISTING);
    } catch (Exception e) {
      if (e instanceof FileAlreadyExistsException) {
        filesStorageLogger.error("A file of that name already exists.", e);
      }

      filesStorageLogger.error("Could not store the file. Please try again!", e);
    }
  }

  @Override
  public boolean doesAvatarExist(String originalFilename) {
    return false;
  }

}