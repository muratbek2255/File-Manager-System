package com.example.savefile;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;


@Service
@PropertySource("classpath:application.properties")
public class UploadFileService {

    @Value("${custom.basedir}")
    private String stringValue;

    private final Optional<Path> root = Optional.of(Path.of("${custom.basedir}"));


    public void save(MultipartFile file, String appName, Integer accountId) {
        try {
            String uuidFile = accountId + UUID.randomUUID().toString();
            Date date = new Date();
            String resultFilename = file.getName() + appName + date + uuidFile + "." + file.getOriginalFilename();
            Files.copy(file.getInputStream(), this.root.orElseThrow().resolve(resultFilename));
        }catch (Exception e) {
            if(e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists");
            }
            throw new RuntimeException(e.getMessage());
        }
    }
}
