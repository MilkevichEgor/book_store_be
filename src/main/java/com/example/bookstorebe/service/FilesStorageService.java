package com.example.bookstorebe.service;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Interface for managing file storage.
 */
@Service
public interface FilesStorageService {
  public void init();

  public Resource load(String filename);

  public void save(MultipartFile file);

  boolean doesAvatarExist(String originalFilename);
}
