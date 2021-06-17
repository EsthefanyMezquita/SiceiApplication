package mx.uady.sicei.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.validation.Valid;
import mx.uady.sicei.model.Tutoria;
import mx.uady.sicei.model.Request.TutoriaRequest;
import mx.uady.sicei.service.EmailService;
import mx.uady.sicei.service.TutoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TutoriaRest {

  @Autowired
  private TutoriaService tutoriaService;

  @Autowired
  private EmailService emailService;

  @GetMapping("/tutorias")
  public ResponseEntity<List<Tutoria>> getTutoria() {
    List<Tutoria> tutorias = tutoriaService.getTutorias();
    return ResponseEntity.ok().body(tutorias);
  }

  @GetMapping("/tutorias/{id}")
  public ResponseEntity<Tutoria> getTutoriaById(@PathVariable("id") Integer id) {
    Tutoria tutoria = tutoriaService.getTutoriaById(id);
    return ResponseEntity.ok().body(tutoria);
  }

  @GetMapping("/tutorias/alumnos/{alumnoId}/profesores/{profesorId}")
  public ResponseEntity<Tutoria> getTutoria(@PathVariable("alumnoId") Integer alumnoId, @PathVariable("profesorId") Integer profesorId) {
    Tutoria tutoria = tutoriaService.getTutoriaByAlumnoIdAndProfesorId(alumnoId, profesorId);
    return ResponseEntity.ok().body(tutoria);
  }

  @PostMapping("/tutorias")
  public ResponseEntity<Tutoria> postTutoria(@RequestBody @Valid TutoriaRequest request) throws URISyntaxException {
    Tutoria tutoriaCreada = tutoriaService.crearTutoria(request, emailService);
    return ResponseEntity.created(new URI("/tutorias/" + tutoriaCreada.getId())).body(tutoriaCreada);
  }

  @PutMapping("/tutorias")
  public ResponseEntity<Tutoria> putTutoria(@RequestBody @Valid TutoriaRequest request) {
    Tutoria tutoriaActualizada = tutoriaService.actualizarTutoria(request, emailService);
    return ResponseEntity.ok().body(tutoriaActualizada);
  }

  @DeleteMapping("/tutorias/alumnos/{alumnoId}/profesores/{profesorId}")
  public ResponseEntity<Void> deleteTutoria(@PathVariable("alumnoId") Integer alumnoId, @PathVariable("profesorId") Integer profesorId) {
    tutoriaService.eliminarTutoria(alumnoId, profesorId, emailService);
    return ResponseEntity.ok().build();
  }
}
