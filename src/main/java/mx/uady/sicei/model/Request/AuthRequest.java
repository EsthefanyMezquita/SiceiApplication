package mx.uady.sicei.model.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;

import mx.uady.sicei.model.Alumno;

public class AuthRequest {

    @NotEmpty
    @NotNull
    private String usuario;
    
    @NotEmpty
    @NotNull
    private String nombre;

    @NotNull
    @Pattern(regexp="^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$&*]).{8,}$",message="La contrasea debe tener al menos 8 caracteres, una letra, un numero y un simbolo especial")
    @NotEmpty
    private String password;
 
    private String carrera;
    
    private Integer equipo;
    
    public AuthRequest() {
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEquipo() {
        return this.equipo;
    }

    public void setEquipo(Integer equipo) {
        this.equipo = equipo; 
    }

    public String getCarrera() {
        return this.carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera; 
    }

    public AuthRequest(String nombre) {
        this.nombre = nombre;
    }

    public AuthRequest nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    //fin nuevo
    @Override
    public String toString() {
        return "{" +
            " nombre='" + getNombre() + "'" +
            "}";
    }
    
}