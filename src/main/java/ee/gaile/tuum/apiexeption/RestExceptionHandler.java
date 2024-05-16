package ee.gaile.tuum.apiexeption;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Aleksei Gaile 17-Apr-24
 */
@Slf4j
@ControllerAdvice
public class RestExceptionHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @ExceptionHandler(DuplicateKeyException.class)
    protected ResponseEntity<String> handleApiError(DuplicateKeyException e) throws JsonProcessingException {
        log.error(e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
                .body(objectMapper.writeValueAsString("user with this customer id already exists"));
    }

    @ExceptionHandler(ApiException.class)
    protected ResponseEntity<String> handleApiError(ApiException e) throws IOException {
        log.error(e.getMessage());

        return ResponseEntity.status(e.getStatus()).contentType(MediaType.APPLICATION_JSON)
                .body(convertErrorToJSON(e));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Map<String, List<String>>> handleApiError(MethodArgumentNotValidException e) {
        Map<String, List<String>> error = new HashMap<>();

        List<String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        error.put("errors", errors);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<String> handleApiError(HttpMessageNotReadableException e) throws IOException {
        log.error(e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
                .body("Invalid input provided - " + convertErrorToJSON(e));
    }

    private String convertErrorToJSON(Exception e) throws IOException {
        Map<String, String> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now().toString());
        error.put("message", e.getMessage());

        return objectMapper.writeValueAsString(error);
    }

}
