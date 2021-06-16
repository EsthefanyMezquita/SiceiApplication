package mx.uady.sicei.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import mx.uady.sicei.exception.NotFoundException;
import mx.uady.sicei.model.Alumno;
import mx.uady.sicei.model.Profesor;
import mx.uady.sicei.model.Tutoria;
import mx.uady.sicei.model.TutoriaLlave;
import mx.uady.sicei.model.Request.TutoriaRequest;
import mx.uady.sicei.repository.AlumnoRepository;
import mx.uady.sicei.repository.ProfesorRepository;
import mx.uady.sicei.repository.TutoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
@Service
public class TutoriaService {

  @Autowired
  private TutoriaRepository tutoriaRepository;

  @Autowired
  private AlumnoRepository alumnoRepository;

  @Autowired
  private ProfesorRepository profesorRepository;
  
  @Autowired
  private AuthService authService;

  public List<Tutoria> getTutorias() {
    List<Tutoria> tutorias = new LinkedList<>();

    tutoriaRepository.findAll().iterator().forEachRemaining(tutorias::add);

    return tutorias;
  }

  public Tutoria getTutoria(TutoriaLlave id) {
    Optional<Tutoria> tutoriaEncontrada = tutoriaRepository.findById(id);

    if (!tutoriaEncontrada.isPresent()) {
      throw new NotFoundException();
    }

    return tutoriaEncontrada.get();
  }

  public List<Tutoria> getTutoriaByIdAlumno(Integer alumnoId) {
    List<Tutoria> tutorias = new LinkedList<>();

    tutorias = tutoriaRepository.findByAlumnoId(alumnoId);

    return tutorias;
  }

  public List<Tutoria> getTutoriaByIdProfesor(Integer profesorId) {
    List<Tutoria> tutorias = new LinkedList<>();

    tutorias = tutoriaRepository.findByProfesorId(profesorId);

    return tutorias;
  }

  @Transactional
  public Tutoria crearTutoria(TutoriaRequest request) {

    Tutoria tutoria = new Tutoria();

    Alumno alumno = this.alumnoExist(request.getId().getIdAlumno());
    Profesor profesor = this.profesorExist(request.getId().getIdProfesor());

    tutoria.setId(request.getId());
    tutoria.setAlumno(alumno);
    tutoria.setProfesor(profesor);
    tutoria.setHoras(request.getHoras());
    tutoria = tutoriaRepository.save(tutoria);

    return tutoria;
  }

  private Alumno alumnoExist(Integer alumnoId) {
    Optional<Alumno> alumnoExist = alumnoRepository.findById(alumnoId);

    if(!alumnoExist.isPresent()) {
      throw new NotFoundException("El alumno no pudo ser encontrado");
    }

    return alumnoExist.get();
  }

  private Profesor profesorExist(Integer profesorId) {
    Optional<Profesor> profesorExist = profesorRepository.findById(profesorId);

    if(!profesorExist.isPresent()) {
      throw new NotFoundException("El profesor no pudo ser encontrado");
    }

    return profesorExist.get();
  }

  @Transactional
  public void eliminarTutoria(TutoriaLlave id) {
    Tutoria tutoriaEliminada = getTutoria(id);

    tutoriaRepository.delete(tutoriaEliminada);

    /*authService.enviarCorreo("Alumno "+tutoriaEliminada.getAlumno().getNombre()+", su tutoria fue registrada como eliminada. \n Profesor: "+
    tutoriaEliminada.getProfesor().getNombre()
    +" \n Horas: " + tutoriaEliminada.getHoras().toString(),
    tutoriaEliminada.getAlumno().getUsuario().getEmail(),
     "Eliminada");*/
  }

  @Transactional
  public Tutoria actualizarTutoria(TutoriaLlave id, TutoriaRequest request) {
    Tutoria tutoriaEncontrada = getTutoria(id);

    tutoriaEncontrada.setHoras(request.getHoras());
    tutoriaRepository.save(tutoriaEncontrada);

    return tutoriaEncontrada;
  }
}