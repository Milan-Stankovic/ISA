package com.isa.ISA.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadService {

    private final Path rootLocation = Paths.get("uploaded");

    public String add(MultipartFile file){

        String fileName = UUID.randomUUID().toString();
        String[] tokens = file.getOriginalFilename().split("\\.(?=[^\\.]+$)");
        String extension = tokens[1];
        String fullName = fileName;//+"."+extension;
        System.out.println(fullName);
        try {
            Files.copy(file.getInputStream(), this.rootLocation.resolve(fullName));
            return fullName;
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("FAIL!");
        }
    }
}
