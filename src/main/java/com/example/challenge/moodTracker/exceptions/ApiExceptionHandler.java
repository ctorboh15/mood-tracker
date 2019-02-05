package com.example.challenge.moodTracker.exceptions;

import com.example.challenge.moodTracker.exceptions.custom.BadRequestException;
import com.example.challenge.moodTracker.exceptions.custom.EntityNotFoundException;
import com.example.challenge.moodTracker.exceptions.custom.UnAuthorizedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<?> handleAllExceptions(Exception ex, WebRequest request) {
        return buildResponseError(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR), request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<?> handleUserNotFoundException(EntityNotFoundException ex, WebRequest request) {
        return buildResponseError(new ApiError(HttpStatus.NOT_FOUND, ex), request);
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<?> handleBadRequestException(BadRequestException ex, WebRequest request) {
        return buildResponseError(new ApiError(HttpStatus.BAD_REQUEST, ex), request);
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public final ResponseEntity<?> handleUnAuthorizedException(UnAuthorizedException ex, WebRequest request){
        return buildResponseError(new ApiError(HttpStatus.UNAUTHORIZED, ex), request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<Object> errors = ex.getBindingResult()
                .getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());

        return new ResponseEntity<Object>(new ApiError(HttpStatus.BAD_REQUEST, ex, errors), HttpStatus.BAD_REQUEST);

    }

    protected ResponseEntity<?> buildResponseError(ApiError error, WebRequest request){
        error.setUri(request.getDescription(false));
        return new ResponseEntity<>(error, error.getStatus());

    }
}
