package mx.uady.sicei.rest;

import java.util.List;

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


import mx.uady.sicei.model.Profesor;
import mx.uady.sicei.service.ProfesorService;

@RestController // Metaprogramacion
@RequestMapping("/api")
public class ProfesorRest {
    
    @Autowired
    private ProfesorService profesorService;
    @GetMapping("/profesores") // Verbo GET, URL: uady.mx/api/profesores
    public ResponseEntity<List<Profesor>> getProfesores() {
        return ResponseEntity.ok().body(profesorService.getProfesores());
    }

    @PostMapping("/profesores") // ? /alumnos/profesores
    public ResponseEntity<Profesor> crearAlumno(@RequestBody Profesor profesor) throws Exception {
        Profesor profeCreado = profesorService.crearProfesor(profesor);
        return ResponseEntity.ok().body(profeCreado);
    }

     // Path Paramater
     @PutMapping("/profesores/{idEmpleado}") // PUT /profesores/1001930
     public ResponseEntity<Profesor> editarProfesor(@PathVariable String idEmpleado, @RequestBody Profesor profesor) throws Exception {
         Profesor profeEditado = profesorService.editarProfesor(Long.parseLong(idEmpleado),profesor);
         return ResponseEntity.ok().body(profeEditado);
     }
    // Path Paramater
    @GetMapping("/profesores/{idEmpleado}") // GET /profesores/1001930
    public ResponseEntity<Profesor> obtenerProfesor(@PathVariable String idEmpleado) {
    Profesor profeBuscado = profesorService.obtenerProfesor(Long.parseLong(idEmpleado));
    return ResponseEntity.ok().body(profeBuscado);

    }
      // Path Paramater
    @DeleteMapping("/profesores/{idEmpleado}") // DELETE /profesores/1001930
    public ResponseEntity<Void> eliminarProfesor(@PathVariable String idEmpleado) {
        profesorService.eliminarProfesor(Long.parseLong(idEmpleado));
        return ResponseEntity.ok().build();
    }
}
