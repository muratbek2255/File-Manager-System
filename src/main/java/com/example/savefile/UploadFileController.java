package com.example.savefile;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@PropertySource("classpath:application.properties")
public class UploadFileController {

    @Autowired
    private final UploadFileServiceImpl uploadFileService;

    public UploadFileController(UploadFileServiceImpl uploadFileService) {
        this.uploadFileService = uploadFileService;
    }

    @Value("${custom.apps}")
    String value;

    @GetMapping("/get")
    public String newController() {
        System.out.println(value);
        return "Hello!";
    }


    @PostMapping("/in/{appName}/{accountId}")
    public ResponseEntity<UploadFileResponse> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("appName") String appName,
            @RequestParam("accountId") Integer accountId
    ) {
        System.out.println(value);

        String message = "";
        try {
            appName = value;
            uploadFileService.save(file, appName, accountId);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new UploadFileResponse(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new UploadFileResponse(message));
        }


    }
}
