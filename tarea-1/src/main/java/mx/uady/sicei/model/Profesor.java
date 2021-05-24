package mx.uady.sicei.model;

public class Profesor {
    private Long idEmpleado; // NO vacia - null or ""
    private String nombre; // No vacio - null or ""
    private int horasClase;
    private String correo;

    
    public Profesor() {
    }

    public Profesor(Long idEmpleado, String nombre, int horasClase, String correo) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.horasClase = horasClase;
        this.correo = correo;

    }

    public Long getIdEmpleado() {
        return idEmpleado;
    }
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public int getHorasClase() {
        return horasClase;
    }
    public void setHorasClase(int horasClase) {
        this.horasClase = horasClase;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setIdEmpleado(long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
    

    @Override
    public String toString() {
        return "{" +
            " número de empleado='" + getIdEmpleado() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", horas de clase='" + getHorasClase() +"'" +
            ", correo='" + getCorreo() + "'" +
            "}";
    }
}