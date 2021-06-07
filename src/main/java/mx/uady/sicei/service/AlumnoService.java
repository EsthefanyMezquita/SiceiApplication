package mx.uady.sicei.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.uady.sicei.exception.NotFoundException;
import mx.uady.sicei.model.Alumno;
import mx.uady.sicei.model.Equipo;
import mx.uady.sicei.model.Usuario;
import mx.uady.sicei.model.request.AlumnoRequest;
import mx.uady.sicei.repository.AlumnoRepository;
import mx.uady.sicei.repository.UsuarioRepository;

@Service
public class AlumnoService {

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Alumno> getAlumnos() {

        List<Alumno> alumnos = new LinkedList<>();

        alumnoRepository.findAll().iterator().forEachRemaining(alumnos::add); // SELECT(id, nombre)

        return alumnos;
    }

    /**
    * Este es el metodo que devuelve el alumno
    * @param id Integer, id del alumno 
    * @return void
    *
    */
    public Alumno getAlumno(Integer id) {
        Optional<Alumno> alumno = alumnoRepository.findById(id);

        if (!alumno.isPresent()) {
            throw new NotFoundException();
        }

        return alumno.get();
    }

    /**
    * Este es el metodo que busca al alumno por nombre
    * @param nombre String, nombre del alumno
    * @return nombre del alumno
    *
    */
    public List<Alumno> buscarAlumnos(String nombre) {
        return alumnoRepository.findByNombreContaining(nombre);
    }

    /**
    * Este es el metodo que crea un nuevo alumno
    * @param request AlumnoRequest, el request del formulario de alumno
    * @return alumno
    *
    */
    @Transactional
    public Alumno crearAlumno(AlumnoRequest request) {

        Alumno alumno = new Alumno();

        alumno.setNombre(request.getNombre());
        alumno.setCarrera(request.getCarrera());

        Usuario usuario = new Usuario();

        usuario.setUsuario(request.getCorreo());
        usuario.setPassword("123");

        String token = UUID.randomUUID().toString();
        usuario.setToken(token);
        alumno.setUsuario(usuario);

        usuario = usuarioRepository.save(usuario);
        alumno = alumnoRepository.save(alumno); // INSERT

        return alumno;
    }

    /**
    * Este es el metodo que actualiza al alumno
    * @param id Integer, id del alumno
    * @param request AlumnoRequest, el request del formulario de alumno
    * @return alumnoEncontrado
    *
    */
    @Transactional
    public Alumno actualizarAlumno(Integer id, AlumnoRequest request) {
        // Validar equipo
        Alumno alumnoEncontrado = getAlumno(id);
        alumnoEncontrado.setCarrera(request.getCarrera());
        alumnoEncontrado.setNombre(request.getNombre());
        // alumnoEncontrado.setEquipo();
        alumnoRepository.save(alumnoEncontrado);
        return alumnoEncontrado;
    }

    /**
    * Este es el metodo que elimina un alumno
    * @param id Intger, id del alumno
    * @return void 
    *
    */
    @Transactional
    public void eliminarAlumno(Integer id) {
        Usuario usuarioEliminar = getAlumno(id).getUsuario();
        // Validar que no existan tutorias
        usuarioRepository.delete(usuarioEliminar);
        alumnoRepository.deleteById(id);
    }

}
