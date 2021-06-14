package mx.uady.sicei.service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import javax.validation.constraints.Null;

import org.springframework.transaction.annotation.Transactional;

//import io.jsonwebtoken.lang.Objects;
import java.util.Objects;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//Seguridad con JWT
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

//import mx.uady.sicei.exception.*;
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
import mx.uady.sicei.exception.NotFoundException;
import mx.uady.sicei.exception.UnauthorizedException;

@Service
public class AuthService implements UserDetailsService{
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

    @Autowired
	private AuthenticationManager authenticationManager;

    @Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario user = usuarioRepository.findByUsuario(username);

        if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.
            User(user.getUsuario(), user.getPassword(), new ArrayList<>());
        //return new User(user.getUsuario(), user.getPassword(), new ArrayList<>());
	}

    /* @Transactional
    public UsuarioRepository save(AuthRequest authRequest) {
		Usuario newUser = new Usuario();
        newUser.setUsuario(authRequest.getUsuario());
		newUser.setPassword(passwordEncoder.encode(authRequest.getPassword()));
		return usuarioRepository.save(newUser);
	} */

    @Transactional
    public Alumno registrarAlumno(AuthRequest request) {
        Usuario usuarioCreate = new Usuario();
        usuarioCreate.setUsuario(request.getUsuario());
        usuarioCreate.setPassword(passwordEncoder.encode(request.getPassword()));
        // String token = UUID.randomUUID().toString();
        // usuarioCreate.setToken(token);

        Usuario userExistente = usuarioRepository.findByUsuario(usuarioCreate.getUsuario());

        if (!(userExistente ==  null)) {
            throw new UnauthorizedException();
        }

        /* if(!profesorExist.isPresent()) {
            throw new NotFoundException("El profesor no pudo ser encontrado");
          } */

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
            Equipo equipo = equipoRepository.findById(request.getEquipo()).get();
            alumno.setEquipo(equipo);
        }

        if(request.getCarrera()!=null){
            Carrera carrera = Carrera.valueOf(request.getCarrera());
            alumno.setCarrera(carrera);
        }

        alumno.setUsuario(usuarioSave);
        alumno = alumnoRepository.save(alumno);
        return alumno;
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
        Usuario usuario = usuarioRepository.findById(id).get();
        usuario.setToken(null);
        usuarioRepository.save(usuario);
    }
}