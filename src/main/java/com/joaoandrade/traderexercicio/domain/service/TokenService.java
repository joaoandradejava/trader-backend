package com.joaoandrade.traderexercicio.domain.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.joaoandrade.traderexercicio.core.jwt.JwtUtil;
import com.joaoandrade.traderexercicio.domain.exception.NegocioException;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class TokenService {

	@Autowired
	private JwtUtil jwtUtil;

	public String refreshToken(String token) {
		if (token.startsWith("Bearer ")) {
			token = token.replaceAll("Bearer ", "");
		}
		String subject = jwtUtil.getSubject(token);

		if (!StringUtils.hasLength(subject)) {
			throw new NegocioException("Token inválido!");
		}

		return "Bearer " + jwtUtil.gerarTokenJwt(subject);
	}
}
