package com.example.product_api.controller;

import com.example.product_api.dto.ApiResponse;
import com.example.product_api.exception.cart.ItemNotInCartException;
import com.example.product_api.exception.cart.CartNotFoundException;
import com.example.product_api.exception.cart.EmptyCartException;
import com.example.product_api.exception.user.KeycloakUserCreationException;
import com.example.product_api.exception.user.UserAlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@Slf4j
@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        log.warn("Validation failed: {}", ex.getBindingResult().getAllErrors());
        ApiResponse apiResponse = new ApiResponse();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            apiResponse.addError(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse> handleMissingRequestBody(HttpMessageNotReadableException ex) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.addError("request_body", "DTO request data must not be null");
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse> handleConstraintViolation(ConstraintViolationException ex) {
        ApiResponse apiResponse = new ApiResponse();

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String field = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            apiResponse.addError(field, message);
        }

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse> handleEntityNotFound(EntityNotFoundException ex) {

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.addError("entity_not_found", ex.getMessage());

        return ResponseEntity.status(404).body(apiResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse> handleIllegalArgument(IllegalArgumentException ex) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.addError("invalid_argument", ex.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponse> handleUserAlreadyExists(UserAlreadyExistsException ex) {

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.addError("user_already_exists", ex.getMessage());

        return ResponseEntity.status(409).body(apiResponse);
    }

    @ExceptionHandler(KeycloakUserCreationException.class)
    public ResponseEntity<ApiResponse> handleUserCreationError(KeycloakUserCreationException ex) {
        log.info("Conflict detected: {}", ex.getMessage());
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.addError("user_create_error", ex.getMessage());

        return ResponseEntity.status(400).body(apiResponse);
    }

    @ExceptionHandler(EmptyCartException.class)
    public ResponseEntity<ApiResponse> handleCartIsEmpty(EmptyCartException ex) {
        log.info("Cart is Empty {}", ex.getMessage());
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.addError("cart exception: ", ex.getMessage());

        return ResponseEntity.status(400).body(apiResponse);
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ApiResponse> handleCartNotFound(CartNotFoundException ex) {
        log.info("Cart Not Found {}", ex.getMessage());
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.addError("cart exception: ", ex.getMessage());

        return ResponseEntity.status(500).body(apiResponse);
    }

    @ExceptionHandler(ItemNotInCartException.class)
    public ResponseEntity<ApiResponse> handleItemNotInCart(ItemNotInCartException ex) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.addError("item_not_in_cart", ex.getMessage());
        return ResponseEntity.status(404).body(apiResponse);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleUnexpectedException(Exception e){
        log.error("UNEXPECTED ERROR: ", e);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.addError("unknown ex = ", e.getMessage());
        return ResponseEntity.status(500).body(apiResponse);
    } // /todo: log in  JSON format
}
