package com.clases.interactivas.clases_practicas.dto.response;

public class ApiResponse409 {
    private int code;
    private String message;

    public ApiResponse409() {
    }

    public ApiResponse409(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
