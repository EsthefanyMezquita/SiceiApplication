package mx.uady.sicei.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class Unauthorized extends RuntimeException {

    public Unauthorized() {
        super("El usuario ingresado ya existe");
    }

    public Unauthorized(String mensaje) {
        super(mensaje);
    }
    
}
