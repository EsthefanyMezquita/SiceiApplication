package mx.uady.sicei.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import mx.uady.sicei.exception.NotFoundException;
import mx.uady.sicei.model.Alumno;
import mx.uady.sicei.model.Profesor;
import mx.uady.sicei.model.Tutoria;
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

  @Autowired
  private AlumnoService alumnoService;
  
  @Autowired
  private ProfesorService profesorService;

  @Autowired
  private EmailService emailService;

  public List<Tutoria> getTutorias() {
    List<Tutoria> tutorias = new LinkedList<>();

    tutoriaRepository.findAll().iterator().forEachRemaining(tutorias::add);

    return tutorias;
  }

  public Tutoria getTutoriaById(Integer id) {
    Optional<Tutoria> tutoriaEncontrada = tutoriaRepository.findById(id);

    if (!tutoriaEncontrada.isPresent()) {
      throw new NotFoundException();
    }

    return tutoriaEncontrada.get();
  }

  public Tutoria getTutoriaByAlumnoIdAndProfesorId(Integer alumnoId, Integer profesorId) {
    Tutoria tutoriaEncontrada = tutoriaRepository.findByAlumnoIdAndProfesorId(alumnoId, profesorId);

    if (tutoriaEncontrada == null) {
      throw new NotFoundException("No se encuentran en los registros la combinacion de ID's.");
    }

    return tutoriaEncontrada;
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
  public Tutoria crearTutoria(TutoriaRequest request, EmailService emailService) {

    Tutoria tutoria = new Tutoria();

    try{
      Alumno alumno = alumnoService.getAlumno(request.getAlumnoId());
      Profesor profesor = profesorService.getProfesor(request.getProfesorId());

      tutoria.setAlumno(alumno);
      tutoria.setProfesor(profesor);
      tutoria.setHoras(request.getHoras());

      tutoria = tutoriaRepository.save(tutoria);
      emailService.sendEmail("Se te asignó una tutoria:\nMaestro"+ tutoria.getProfesor().getNombre() +
                              "\nDuración: " + tutoria.getHoras()+" hora(s)",
                              tutoria.getAlumno().getUsuario().getEmail(), "Tutoria asignada");
    } catch(Exception ex) {
        System.out.print(ex.getMessage());
    }
    return tutoria;
  }

  @Transactional
  public Tutoria actualizarTutoria(TutoriaRequest tutoriaRequest, EmailService emailService) {
    Tutoria tutoriaEncontrada = tutoriaRepository.findByAlumnoIdAndProfesorId(tutoriaRequest.getAlumnoId(), tutoriaRequest.getProfesorId());

    tutoriaEncontrada.setHoras(tutoriaRequest.getHoras());
    tutoriaRepository.save(tutoriaEncontrada);

    emailService.sendEmail("La tutoria con el maestro "+ tutoriaEncontrada.getProfesor().getNombre() +
                              " fue modificada:\nMaestro: "+tutoriaEncontrada.getProfesor().getNombre()+"\nDuración: "+tutoriaEncontrada.getHoras() +" hora(s).",
                              tutoriaEncontrada.getAlumno().getUsuario().getEmail(), "Tutoria actualizada");

    return tutoriaEncontrada;
  }

  @Transactional
  public Tutoria eliminarTutoria(Integer alumnoId, Integer profesorId, EmailService emailService) {
    Tutoria tutoriaEliminada = tutoriaRepository.findByAlumnoIdAndProfesorId(alumnoId, profesorId);

    tutoriaRepository.delete(tutoriaEliminada);

    emailService.sendEmail("La tutoria:\nMaestro: "+ tutoriaEliminada.getProfesor().getNombre() +
                              "\nDuración: " + tutoriaEliminada.getHoras()+" hora(s)\nFue cancelada.",
                              tutoriaEliminada.getAlumno().getUsuario().getEmail(), "Tutoria cancelada");

    return tutoriaEliminada;
  }

  

  private Alumno alumnoExist(Integer alumnoId) {
    Optional<Alumno> alumnoExist = alumnoRepository.findById(alumnoId);

    if(!alumnoExist.isPresent()) {
      throw new NotFoundException("El alumno con id :" + alumnoId+" no existe en los registros.");
    }

    return alumnoExist.get();
  }

  private Profesor profesorExist(Integer profesorId) {
    Optional<Profesor> profesorExist = profesorRepository.findById(profesorId);

    if(!profesorExist.isPresent()) {
      throw new NotFoundException("El profesor con id :" + profesorId+" no existe en los registros.");
    }

    return profesorExist.get();
  }
}