package mx.uady.sicei.rest;

import javax.validation.Valid;

import java.net.URISyntaxException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

//JWT
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import mx.uady.sicei.config.JwtTokenUtil;

import mx.uady.sicei.model.Usuario;
import mx.uady.sicei.model.Request.AuthRequest;
import mx.uady.sicei.model.Request.JwtResponse;
import mx.uady.sicei.service.AuthService;
import mx.uady.sicei.service.EmailService;
import mx.uady.sicei.service.UsuarioService;

@RestController
public class AuthRest {
    private Usuario usuarioLoggeado;

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
        
        return ResponseEntity.ok(authService.registrarAlumno(request, emailService));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> postLogin(@RequestBody AuthRequest request, @RequestHeader("user-agent") String userAgent) throws Exception {
        usuarioLoggeado = authService.login(request, emailService, userAgent, jwtTokenUtil, jwtInMemoryUserDetailsService, authenticationManager);

		return ResponseEntity.ok().body(new JwtResponse(usuarioLoggeado.getToken()));
    }

    @PostMapping("/logout/{id}")
    public ResponseEntity<Void> postLogout(@PathVariable Integer id ) throws URISyntaxException {
        authService.logout(id);
        usuarioLoggeado=null;
       return ResponseEntity.noContent().build();
    }

    @GetMapping("/self")
    public ResponseEntity<Object> getLoggerdUser() {
        return ResponseEntity.status(HttpStatus.OK).body(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

}