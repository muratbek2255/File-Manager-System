package com.example.savefile;

public class UploadFileResponse {
    private String message;

    public UploadFileResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
