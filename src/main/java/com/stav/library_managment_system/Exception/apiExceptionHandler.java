package com.stav.library_managment_system.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
 @ControllerAdvice
public class apiExceptionHandler  {
    @ExceptionHandler(value = {apiRequestException.class})

    public ResponseEntity<Object> handlerApiRequestException(apiRequestException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ApiException ApiException = new ApiException(
             e.getMessage(),
             e,
                badRequest,
             ZonedDateTime.now(ZoneId.of("Z"))
     );
      return new ResponseEntity<>(ApiException, badRequest);
    }
}
