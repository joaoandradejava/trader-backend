package com.joaoandrade.traderexercicio.core.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	@Autowired
	private JwtConfig jwtConfig;

	public String gerarTokenJwt(String username) {
		return Jwts.builder().setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getTempoExpiracao()))
				.signWith(SignatureAlgorithm.HS512, jwtConfig.getSenha().getBytes()).compact();
	}

	public boolean isTokenValido(String token) {
		Claims claims = getClaims(token);

		if (claims != null) {
			String subject = claims.getSubject();
			Date expirationDate = claims.getExpiration();
			Date now = new Date();

			if (StringUtils.hasLength(subject) && expirationDate != null && now.before(expirationDate)) {
				return true;
			}
		}

		return false;
	}

	public String getSubject(String token) {
		Claims claims = getClaims(token);

		return claims != null ? claims.getSubject() : null;
	}

	private Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(jwtConfig.getSenha().getBytes()).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			return null;
		}
	}

}
