package winwin.data_api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<Exception> handleException(Exception exception) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        // return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }
}
