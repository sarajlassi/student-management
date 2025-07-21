package org.management.exceptions;

 import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.rmi.ServerException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice

public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgumentException(
            IllegalArgumentException illegalArgumentException) {

        ApiError errorDetails = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
               "Malformed JSON in the request body.",
                List.of(illegalArgumentException.getMessage())
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<ApiError> handleServerError(ServerException serverException,
                                                      WebRequest webRequest) {
        ApiError errorDetails = new ApiError(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                webRequest.getDescription(false),
                List.of(serverException.getMessage()));

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(
            Exception exception, WebRequest webRequest) {

        ApiError apiError = new ApiError(LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                webRequest.getDescription(false),
                List.of(exception.getMessage()));
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
    /**
     * Handel validation exception
     *
     * @param methodArgumentNotValidException Field not valid.
     * @return Map contains error message.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleInvalidArgument(
            MethodArgumentNotValidException methodArgumentNotValidException) {
        List<String> errorList = new ArrayList<>();
        methodArgumentNotValidException.getBindingResult().getFieldErrors().forEach((FieldError error) ->
                errorList.add(error.getField() + ": " + error.getDefaultMessage())
        );


        ApiError errorDetails = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Malformed JSON in the request body.",
                errorList
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);}


    /**
     * Handel's exception if request Body is missing
     *
     * @param httpMessageNotReadableException httpMessageNotReadableException
     * @return Map contains error message.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleInvalidArgument(
            HttpMessageNotReadableException httpMessageNotReadableException) {

        ApiError errorDetails = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Malformed JSON in the request body.",
                List.of(httpMessageNotReadableException.getMessage())
        );


        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }


}
