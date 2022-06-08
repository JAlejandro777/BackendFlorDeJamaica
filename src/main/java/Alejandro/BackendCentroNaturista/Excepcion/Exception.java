package Alejandro.BackendCentroNaturista.Excepcion;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Exception extends RuntimeException{
    private String code;
    public Exception(String code, String message) {
        super(message);
        this.code = code;
    }
}
