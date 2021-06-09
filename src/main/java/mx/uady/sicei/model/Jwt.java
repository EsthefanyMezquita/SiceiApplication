package mx.uady.sicei.model;
import java.io.Serializable;

public class Jwt implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;
    private String token;

    public Jwt(String token) {
        this.token = token;
    }

    /**
    * Obtiene el token
    *
    */
    public String getToken() {
        return this.token;
    }

    /**
    * Devuelve el token
    * @param token String
    *
    */
    public void setToken(String token) {
        this.token = token;
    }
}