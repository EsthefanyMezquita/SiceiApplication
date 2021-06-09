package mx.uady.sicei.config;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import mx.uady.sicei.model.Usuario;
import mx.uady.sicei.repository.UsuarioRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired 
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
    HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        // El token existe y es valido
        if(jwtToken.isEmpty() || !jwtTokenUtil.validateStructure(jwtToken) || jwtToken == null ) {
            chain.doFilter(request, response);
            return;
        }

        String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        Usuario usuario = usuarioRepository.findByUsuario(username);

        // El token corresponde a un usuario
        if(usuario == null || !jwtTokenUtil.validateToken(jwtToken, usuario)) {
            chain.doFilter(request, response);
            return;
        }

        // El token existe para el usuario determinado
        if(usuario.getToken() == null || usuario.getToken().isEmpty()) {
            chain.doFilter(request, response);
            return;
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(usuario, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}