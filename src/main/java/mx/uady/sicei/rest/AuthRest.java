package mx.uady.sicei.rest;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//JWT
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.http.ResponseEntity;

import mx.uady.sicei.config.JwtTokenUtil;
import mx.uady.sicei.model.Alumno;
import mx.uady.sicei.model.Usuario;
import mx.uady.sicei.model.Request.UsuarioRequest;
import mx.uady.sicei.model.Request.AuthRequest;
import mx.uady.sicei.model.Request.JwtResponse;
import mx.uady.sicei.model.Request.AlumnoRequest;
import mx.uady.sicei.service.AuthService;
import mx.uady.sicei.service.EmailService;
import mx.uady.sicei.service.UsuarioService;
import mx.uady.sicei.service.AlumnoService;

@RestController
public class AuthRest {

    @Autowired
    private AuthService authService;

    @Autowired
	private JwtTokenUtil jwtTokenUtil;

    @Autowired
	private UserDetailsService jwtInMemoryUserDetailsService;

    @Autowired
	private AuthenticationManager authenticationManager;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<?> postRegister(@RequestBody @Valid AuthRequest request)throws Exception {
        /* Alumno alumno = authService.registrarAlumno(request);
        return ResponseEntity.created(new URI("alumnos" + alumno.getId())).body(alumno); */
        emailService.sendEmail("<b>Bienvenido " +request.getUsuario() +"!<b>\nTu registro se realizó correctamente.", request.getEmail(), "Hola " +request.getNombre());
        return ResponseEntity.ok(authService.registrarAlumno(request));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> postLogin(@RequestBody AuthRequest request, @RequestHeader("user-agent") String userAgent) throws Exception {
        //String token = authService.login(request);

        authenticate(request.getUsuario(), request.getPassword());

		final UserDetails userDetails = jwtInMemoryUserDetailsService.loadUserByUsername(request.getUsuario());
		final String token = jwtTokenUtil.generateToken(userDetails);
        
        emailService.sendEmail("Se ha iniciado sesion desde: " +userAgent, usuarioService.getUsuarioByUsername(request.getUsuario()).getEmail(), "Inicio de sesión");

		return ResponseEntity.ok().body(new JwtResponse(token));
    }

    public void authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

    @PostMapping("/logout/{id}")
    public ResponseEntity<Void> postLogout(@PathVariable Integer id ) throws URISyntaxException {
        authService.logout(id);
       return ResponseEntity.noContent().build();
    }

    @GetMapping("/self")
    public ResponseEntity<Usuario> getLoggerdUser() {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(usuario);
    }

}