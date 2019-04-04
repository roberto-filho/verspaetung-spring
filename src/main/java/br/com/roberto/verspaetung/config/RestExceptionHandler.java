package br.com.roberto.verspaetung.config;

import br.com.roberto.verspaetung.dto.GenericMessageDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final String message = String.format("Missing [%s] parameter.", ex.getParameterName());

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new GenericMessageDTO(message));
    }
}
