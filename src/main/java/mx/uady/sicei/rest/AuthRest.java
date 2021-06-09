package mx.uady.sicei.rest;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
import mx.uady.sicei.model.request.UsuarioRequest;
import mx.uady.sicei.model.request.AuthRequest;
import mx.uady.sicei.model.request.JwtResponse;
import mx.uady.sicei.model.request.AlumnoRequest;
import mx.uady.sicei.service.AuthService;
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

    @PostMapping("/register")
    public ResponseEntity<Alumno> postRegister(@RequestBody @Valid AuthRequest request)throws URISyntaxException {
        Alumno alumno = authService.registrarAlumno(request);
        return ResponseEntity.created(new URI("alumnos" + alumno.getId())).body(alumno);
    }

    @PostMapping("/login")
    public ResponseEntity<String> postLogin(@RequestBody AuthRequest request) throws Exception {
        //String token = authService.login(request);

        authenticate(request.getUsuario(), request.getPassword());

		final UserDetails userDetails = jwtInMemoryUserDetailsService.loadUserByUsername(request.getUsuario());
		final String token = jwtTokenUtil.generateToken(userDetails);
        //JwtResponse formatToken = new JwtResponse(token);

		return ResponseEntity.ok(token);
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