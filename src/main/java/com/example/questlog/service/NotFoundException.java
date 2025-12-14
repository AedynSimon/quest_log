package com.example.questlog.service;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String type, long id) {
        super(type + " not found: " + id);
    }
}
