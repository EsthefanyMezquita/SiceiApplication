package mx.uady.sicei.model.Request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;

public class UsuarioRequest {

    @NotNull
    @Size(min = 5, max = 50)
    @NotEmpty
    private String usuario;

    @NotNull
    @Size(min = 1, max = 255)
    @NotEmpty
    private String nombre;

    @NotNull
    @Size(min = 5, max = 50)
    @NotEmpty
    private String password;

    @NotNull
    @Pattern(regexp="^[^@]+@[^@]+[a-zA-Z]{2,}$",message="El correo electronico es incorrecto")
    @NotEmpty
    private String email;

    public UsuarioRequest() {

    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email=email;
    }
}