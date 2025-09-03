package br.com.neoapp.clientapi.security;

import br.com.neoapp.clientapi.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    /**
     * Gera um novo token JWT para o usuário fornecido.
     *
     * @param usuario O usuário para o qual o token será gerado.
     * @return Uma string representando o token JWT.
     */
    public String createToken(User usuario) {
       
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        Instant agora = Instant.now();
        Instant expiracao = agora.plus(2, ChronoUnit.HOURS);

        return Jwts.builder()
                .setIssuer("client-api") 
                .setSubject(usuario.getUsername()) 
                .setIssuedAt(Date.from(agora)) 
                .setExpiration(Date.from(expiracao)) 
                .signWith(key, SignatureAlgorithm.HS256) 
                .compact(); 
    }

    /**
     * Valida o token JWT e extrai o "subject" (nome do usuário).
     *
     * @param token A string do token JWT a ser validada.
     * @return O nome de usuário (subject) se o token for válido, ou null caso contrário.
     */
    public String getSubjectDoToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (Exception e) {
            return null;
        }
    }
}