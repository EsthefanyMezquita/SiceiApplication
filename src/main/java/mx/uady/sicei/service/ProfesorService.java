package mx.uady.sicei.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uady.sicei.model.Profesor;
import mx.uady.sicei.model.Request.ProfesorRequest;
import mx.uady.sicei.repository.ProfesorRepository;

@Service
public class ProfesorService {
    
    @Autowired
    private ProfesorRepository profesorRepository;

    public List<Profesor> getProfesores() {

        List<Profesor> profesores = new LinkedList<>();

        profesorRepository.findAll().iterator().forEachRemaining(profesores::add);

        return profesores;
    }

    public List<Profesor> buscarProfesores(String nombre) {
        return profesorRepository.findByNombreContaining(nombre);
    }

    public Profesor crearProfesor(ProfesorRequest request) {
        Profesor profesor = new Profesor();

        profesor.setNombre(request.getNombre());
        profesor.setHoras(request.getHoras());
        profesor = profesorRepository.save(profesor);

        return profesor;
    }

    public Profesor getProfesor(Integer id) {
        return profesorRepository.findById(id).get();//findByIdSerializable(id).get();
    }

    public Profesor actualizarProfesor(Integer id, ProfesorRequest profesor) {
        Profesor profesorAct = getProfesor(id);

        Profesor profesorReference = profesorRepository.findByNombre(profesorAct.getNombre()).get(0);
        profesorReference = setProfesorNewValues(profesorReference, profesor);
        return profesorRepository.save(profesorReference);
    }

    private Profesor setProfesorNewValues(Profesor profesorRef,ProfesorRequest profesorEdit){
        profesorRef.setNombre(profesorEdit.getNombre());
        profesorRef.setHoras(profesorEdit.getHoras());
        return profesorRef;
    }

    public Profesor eliminarProfesor(Integer id){
        Profesor profeEliminar = profesorRepository.findById(id).get();
        profesorRepository.deleteById(profeEliminar.getId());
        return profeEliminar;
    }

}
