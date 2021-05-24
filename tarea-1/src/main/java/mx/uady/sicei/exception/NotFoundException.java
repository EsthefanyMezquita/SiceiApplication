package mx.uady.sicei.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    // RuntimeException VS Exception
    // Unchecked Exceptions VS Checked Exceptions

    public NotFoundException() {
        super("La entidad no pudo ser encontrada.");
    }

    public NotFoundException(String mensaje) {
        super(mensaje);
    }
    
}