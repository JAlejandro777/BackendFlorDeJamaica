package Alejandro.BackendCentroNaturista.Excepcion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorDTO> runtimeExceptionHandler(Exception e ){
        ErrorDTO error = ErrorDTO.builder().code(e.getCode()).message(e.getMessage()).build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
