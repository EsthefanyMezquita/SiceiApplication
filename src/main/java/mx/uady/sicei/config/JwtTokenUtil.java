package mx.uady.sicei.config;

import mx.uady.sicei.model.Usuario;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil implements Serializable{
    private static final long serialVersionUID = -2550185165626007488L;
    public static final long JWT_TOKEN_VALIDITY = 1 * 60 * 60;
    
    @Value("${jwt.secret}")
    private String secret;

    /**
    * Recibe el username del JWT token
    * @param token token
    * @return void
    *
    */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
    * Recibe la fecha de expiración del JWT token
    * @param token token
    *
    */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
    * 
    * @param token token
    * @param claimsResolver Function<Claims,T>
    *
    */
    public <T>T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);

        return claimsResolver.apply(claims);
    }

    /**
    * Para recuperar la información del token
    * @param token String
    *
    */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret.getBytes(Charset.forName("UTF-8"))).parseClaimsJws(token).getBody();
    }

    /**
    * Revisa si el token ya expiro, el token dura 1 hora
    * @param token String
    *
    */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);

        return expiration.before(new Date());
    }

    /**
    * Genera un token para el usuario
    * @param usuario Usuario
    *
    */
    public String generateToken(Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();

        return doGenerateToken(claims, usuario.getUsuario());
    }

    /**
    * 
    * @param subject String
    * @param claims Map <String,Object>
    *
    */
    private String doGenerateToken(Map<String, Object> claims, String subject){
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
            .signWith(SignatureAlgorithm.HS256, secret.getBytes(Charset.forName("UTF-8"))).compact();
    }

    /**
    * Valida el token
    * @param usuario Usuario
    * @param token String
    *
    */
    public Boolean validateToken(String token, Usuario usuario) {
        final String username = getUsernameFromToken(token);

        return (username.equals(usuario.getUsuario()) && !isTokenExpired(token));
    }
}