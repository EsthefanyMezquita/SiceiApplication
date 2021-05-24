package mx.uady.sicei.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import mx.uady.sicei.exception.NotFoundException;

import mx.uady.sicei.model.Alumno;

@Service
public class AlumnoService {

    private List<Alumno> alumnos = new LinkedList<>();

    {
        alumnos.add(new Alumno("100001940", "Eduardo Rodriguez"));
        alumnos.add(new Alumno("100001941", "Eduardo"));
        alumnos.add(new Alumno("100001941", "Eduardo"));
    }
    
    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    public void validarAlumnoVacio(Alumno alumno) throws Exception{
        if (alumno.getMatricula()==null || alumno.getNombre()==null ){
            throw new Exception("El campo no puede ser vacio");

        }
    }
    public Alumno crearAlumno(Alumno alumno) throws Exception  {
        validarAlumnoVacio(alumno);
        alumnos.add(alumno);

        return alumno;
    }
    public Alumno editarAlumno(String matricula, Alumno alumno) throws Exception {
        validarAlumnoVacio(alumno);        
        for (Alumno alumnoi : alumnos) {
            if (alumnoi.getMatricula().equals(matricula)) {
                alumnos.set(alumnos.indexOf(alumnoi), alumno);
                return alumno;
            }
        }
        return null;
    }

    public Alumno obtenerAlumno(String matricula) {
        return alumnos.stream()
        .filter(student -> student.getMatricula().equals(matricula))
        .findFirst()
        .orElseThrow(() -> new NotFoundException());
}

// throw new Exception("El campo no puede ser vacio")
        
    
    public void eliminarAlumno(String matricula) {
        for (Alumno alumno : alumnos) {
            if (alumno.getMatricula().equals(matricula)) {
                alumnos.remove(alumno);
            }
        }
    }
    // throw new Exception("El campo no puede ser vacio")

}