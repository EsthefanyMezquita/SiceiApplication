package mx.uady.sicei.rest;

import java.util.List;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.uady.sicei.model.Usuario;
import mx.uady.sicei.model.request.UsuarioRequest;
import mx.uady.sicei.service.UsuarioService;

@RestController
@RequestMapping("/api")
public class UsuarioRest {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/quienSoy") // /self
    public ResponseEntity<Usuario> getLoggedUser() {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<Jwt> loginUsuario(@RequestBody @Valid LoginRequest request) {
        String token = usuarioService.loginUser(request);

        return ResponseEntity.ok().body(new Jwt(token));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logoutUsuario(HttpServletRequest request, HttpServletResponse response){
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        usuarioService.logoutUser(usuario);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> obtenerUsuario() {
        List<Usuario> usuarios = usuarioService.getUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping("/usuarios")
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody UsuarioRequest request) throws URISyntaxException{
        Usuario u = usuarioService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(u);
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> getUsuario(@PathVariable Integer id) {
        Usuario u = usuarioService.getUsuario(id);
        return ResponseEntity.status(HttpStatus.OK).body(u);
    }

   

}