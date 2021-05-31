package mx.uady.sicei.service;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uady.sicei.exception.*;
import mx.uady.sicei.model.Alumno;
import mx.uady.sicei.model.Usuario;
import mx.uady.sicei.model.Equipo;
import mx.uady.sicei.model.Carrera;
import mx.uady.sicei.model.Tutoria;
import mx.uady.sicei.model.request.AlumnoRequest;
import mx.uady.sicei.model.request.UsuarioRequest;
import mx.uady.sicei.model.request.AuthRequest;
import mx.uady.sicei.repository.AlumnoRepository;
import mx.uady.sicei.repository.UsuarioRepository;
import mx.uady.sicei.repository.EquipoRepository;
import mx.uady.sicei.repository.TutoriaRepository;

@Service
public class AuthService{
    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EquipoRepository equipoRepository;

    @Autowired
    private TutoriaRepository tutoriaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Alumno registrarAlumno(AuthRequest request){
        Usuario usuarioCreate = new Usuario();

        usuarioCreate.setUsuario(request.getUsuario());
        usuarioCreate.setPassword(passwordEncoder.encode(request.getPassword()));
        String token = UUID.randomUUID().toString();
        usuarioCreate.setToken(token);

        /*
        POST /register. Crea un Alumno con todo y Usuario, de igual 
        forma crea el Token (UUID). Recibe los campos olbigatorios de Alumno + Contraseña. 
        El usuario del sistema es la matricula del alumno. Validar que no exista el Alumno 
        ni el Usuario. La contraseña debe mayor de 8 caracteres y contener letras, numeros y 
        al menos 1 caracter especial. Guardar la contraseña usando Bcrypt (no guardar 
        la contraseña en texto plano).
        */

        /*Regex para contraseñas del patron solicitado, el maximo de caracteres es 20, se puede modificar
        ^(?=.*\d)(?=.*[\u0021-\u002b\u003c-\u0040])(?=.*[A-Z])(?=.*[a-z])\S{8,}$
        ^(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$&*]).{8,}$
        ^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$&*]).{8,}$
        El patron anterior incluye mayusculas y minusculas, para uno solo de minusculas:
        ^(?=.*\d)(?=.*[\u0021-\u002b\u003c-\u0040])(?=.*[a-z])\S{8,}$
        ^(?=.*[a-z])(?=.*[!@#$&*]).{8,}$
        ^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$&*]).{8,}$
        */

        Usuario usuarioSave = usuarioRepository.save(usuarioCreate);
        Alumno alumno = new Alumno();
        alumno.setNombre(request.getNombre());

        if(request.getEquipo()!=null && request.getEquipo()>0){
            Equipo equipo = equipoRepository.findById(request.getEquipo());
            alumno.setEquipo(equipo);
        }

        if(request.getCarrera()!=null){
            Carrera carrera = Carrera.valueOf(request.getCarrera());
            alumno.setCarrera(carrera);
        }

        alumno.setUsuario(uusuarioSave);
        alumno = alumnoRepository.save(alumno);
        return alumno
    }

    @Transactional
    public String login(UsuarioRequest request){
        Usuario usuario = usuarioRepository.findByUsuario(request.getUsuario());

        if(usuario==null || !passwordEncoder.matches(request.getPassword(), usuario.getPassword())){
            throw new NotFoundException();
        }

        String token = UUID.randomUUID().toString();
        usuario.setToken(token);
        usuario = usuarioRepository.save(usuario);
        return token;
    }

    @Transactional
    public void logout(Integer id){
        Usuario usuario = usuarioRepository.findById(id);
        usuario.setToken(null);
        usuarioRepository.save(usuario);
    }
}