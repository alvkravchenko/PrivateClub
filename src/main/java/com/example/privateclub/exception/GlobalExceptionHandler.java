package com.example.privateclub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // собирает обработчики для всего приложения
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class) // перехват исключения типо NotFound
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException e) {
        // Класс ResponseEntity, который представляет весь HTTP-ответ, тело этого ответа будет типа ErrorResponse
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
        // вернуть статус 404(NOT_FOUND) и положить в тело ответа сообщение из ошибки
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflict(ConflictException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(e.getMessage()));
    }
}
