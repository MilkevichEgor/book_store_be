package com.example.bookstorebe;

import com.example.bookstorebe.service.FilesStorageService;
import com.example.bookstorebe.config.MvcConfig;
import com.example.bookstorebe.socket.SocketEmitter;
import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@SpringBootApplication
@Import(MvcConfig.class)
public class BookStoreBeApplication implements CommandLineRunner {

	@Resource
	FilesStorageService storageService;

	@Resource
	SocketEmitter socketEmitter;

	public static void main(String[] args) {
		SpringApplication.run(BookStoreBeApplication.class, args);
	}

	@Override
	public void run(String... arg) throws Exception {
//      storageService.deleteAll();
		socketEmitter.socketStart();
		storageService.init();
	}

}

