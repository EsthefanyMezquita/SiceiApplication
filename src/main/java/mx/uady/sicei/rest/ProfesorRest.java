package mx.uady.sicei.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import mx.uady.sicei.model.Profesor;
import mx.uady.sicei.model.request.ProfesorRequest;
import mx.uady.sicei.service.ProfesorService;

@RestController // Metaprogramacion
@RequestMapping("/api")
public class ProfesorRest {

    @Autowired
    private ProfesorService profesorService;

    // GET /api/alumnos
    @GetMapping("/profesores")
    public ResponseEntity<List<Profesor>> getProfesores() {
        return ResponseEntity.ok().body(profesorService.getProfesores());
    }

    @GetMapping("/profesores/buscar") // RequestParam = Query parameter -> ?llave=valor&llave=valor
    public ResponseEntity<List<Profesor>> searchProfesores(@RequestParam("nombre") String nombre) {
        return ResponseEntity.ok().body(profesorService.buscarProfesores(nombre));
    }

    @PostMapping("/profesores") // POST api/profesores
    public ResponseEntity<Profesor> postProfesores(@RequestBody @Valid ProfesorRequest request) throws URISyntaxException {

        Profesor profesor = profesorService.crearProfesor(request);

        // 201 Created
        // Header: Location
        return ResponseEntity.created(new URI("/profesores/" + profesor.getId())).body(profesor);
    }

    @PutMapping("/profesores/{id}") // PUT api/profesores/{id}
    public ResponseEntity<Profesor> editarPorfesor(@PathVariable("id") Integer id, @RequestBody ProfesorRequest profesor) {
        return ResponseEntity.ok().body(profesorService.actualizarProfesor(id,profesor));
    }

    @DeleteMapping("/profesores/{id}")
    public ResponseEntity<Profesor> eliminarProfesor(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(profesorService.eliminarProfesor(id));
    }
    
}