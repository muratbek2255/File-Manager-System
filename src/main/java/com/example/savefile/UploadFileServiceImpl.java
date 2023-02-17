package com.example.savefile;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;


@Service
public class UploadFileServiceImpl implements UploadFileService{

    @Value("${custom.basedir}")
    private String stringValue;

    private final Path root = Path.of(stringValue);



    @Override
    public void init() {
        try {
            Files.createDirectories(root);
        }catch (IOException e) {
            throw  new RuntimeException("Файл не инициализирован для upload!");
        }
    }

    @Override
    public void save(MultipartFile file, String appName, Integer accountId) {
        try {
            String uuidFile = accountId + UUID.randomUUID().toString();
            Date date = new Date();
            String resultFilename = file.getName() + appName + date + uuidFile + "." + file.getOriginalFilename();
            Files.copy(file.getInputStream(), this.root.resolve(resultFilename));
        }catch (Exception e) {
            if(e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists");
            }
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if(resource.exists() || resource.isReadable()) {
                return resource;
            }else {
                throw new RuntimeException("Could not read the file");
            }
        }catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        }catch (IOException e) {
            throw new RuntimeException("Could not load files!");
        }
    }
}
