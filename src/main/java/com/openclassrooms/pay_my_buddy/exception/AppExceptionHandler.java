package com.openclassrooms.pay_my_buddy.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String ERROR = "error";
    private static final String ID = "id";

    @ExceptionHandler(NoSuchResourceException.class)
    public ResponseEntity<Map<String, Object>> handleNoSuchResourceException(NoSuchResourceException e) {
        Map<String, Object> body = new HashMap<>();
        body.put(ERROR, "NO_SUCH_RESOURCE");
        body.put("resource", e.getResourceClass().getSimpleName());
        if (Objects.nonNull(e.getId())) {
            body.put(ID, e.getId());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

}