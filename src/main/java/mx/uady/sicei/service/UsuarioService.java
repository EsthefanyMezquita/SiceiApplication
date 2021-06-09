package mx.uady.sicei.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uady.sicei.exception.NotFoundException;
import mx.uady.sicei.model.Alumno;
import mx.uady.sicei.model.Usuario;
import mx.uady.sicei.model.request.UsuarioRequest;
import mx.uady.sicei.repository.AlumnoRepository;
import mx.uady.sicei.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Transactional //(readOnly = true)
    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }

    public Alumno usuarioActivo(Usuario user) {
        Alumno usuarioActivo = this.alumnoRepository.findByUsuario_Id(user.getId());

        if (usuarioActivo == null) {
            throw new NotFoundException("No se encontro el alumno buscado");
        }
        return usuarioActivo;
    }

    public String loginUser(LoginRequest request) {
        Usuario usuarioLoggeado = usuarioRepository.findByUsuario(request.getMatricula());

        if(usuarioLoggeado.equals(null)){
            throw new NotFoundException("Usuario incorrecto");
        }

        //Hay que agregar un metodo para contraseña incorrecta

        String token = jwtTokenUtil.generateToken(usuarioLoggeado);
        usuarioLoggeado.setToken(token);
        usuarioRepository.save(usuarioLoggeado);

        return token;
    }

    @Transactional
    public void logoutUser(Usuario loggedUser){
        loggedUser.setToken(null);
        usuarioRepository.save(loggedUser);
    } 

    @Transactional // Crear una transaccion
    public Usuario crear(UsuarioRequest request) {
        Usuario usuarioCrear = new Usuario();

        usuarioCrear.setUsuario(request.getUsuario());
        usuarioCrear.setPassword(request.getPassword());

        String token = UUID.randomUUID().toString();
        usuarioCrear.setToken(token);

        Usuario usuarioGuardado = usuarioRepository.save(usuarioCrear);
        
        Alumno alumno = new Alumno();

        alumno.setNombre(request.getNombre());
        alumno.setUsuario(usuarioGuardado); // Relacionar 2 entidades

        alumno = alumnoRepository.save(alumno);

        return usuarioGuardado;
    }

    public Usuario getUsuario(Integer id) {

        Optional<Usuario> opt = usuarioRepository.findById(id);

        if (opt.isPresent()) {
            return opt.get();
        }

        throw new NotFoundException();
    }

}