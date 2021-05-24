package mx.uady.sicei.service;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import mx.uady.sicei.exception.NotFoundException;
import mx.uady.sicei.model.Profesor;

@Service
public class ProfesorService {
    private List<Profesor> profesores = new LinkedList<>();

    {
        profesores.add(new Profesor((long) 1000019407, "Eduardo Rodriguez", 6 ,"ed@gmail.com"));
        profesores.add(new Profesor((long)1000019417, "Eduardo ", 6 ,"ed@gmail.com"));
        profesores.add(new Profesor((long)1000019427, "Eduardo R", 6 ,"ed@gmail.com"));
    }

    public List<Profesor> getProfesores() {
        return profesores;
    }
    public void validarProfeVacio(Profesor profe) throws Exception{
        if ( Long.valueOf(profe.getIdEmpleado())==null||profe.getNombre()==null ||Integer.valueOf(profe.getHorasClase())==null||profe.getCorreo()==null){
            throw new Exception("El campo no puede ser vacio");

        }
    }

    public Profesor crearProfesor(Profesor profe) throws Exception  {
        validarProfeVacio(profe);
        profesores.add(profe);

        return profe;
    }

    public Profesor editarProfesor(Long idEmpleado, Profesor profesor) throws Exception {
        Profesor profeProv= obtenerProfesor(idEmpleado);
        profeProv.setNombre(profesor.getNombre());
        profeProv.setHorasClase(profesor.getHorasClase());
        profeProv.setCorreo(profesor.getCorreo()); 
        return profeProv;
    }

    public Profesor obtenerProfesor(Long idEmpleado) {
        return profesores.stream()
        .filter(profe -> profe.getIdEmpleado().equals(idEmpleado))
        .findFirst()
        .orElseThrow(() -> new NotFoundException());
}

public void eliminarProfesor(Long idEmpleado) {
    for (Profesor profesor : profesores) {
        if (profesor.getIdEmpleado().equals(idEmpleado)) {
            profesores.remove(profesor);
        }
    }
}
}
