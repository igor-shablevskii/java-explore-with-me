package ru.practicum.ewm.error;

public class ConflictException extends RuntimeException {

    public ConflictException(String message) {
        super(message);
    }
}