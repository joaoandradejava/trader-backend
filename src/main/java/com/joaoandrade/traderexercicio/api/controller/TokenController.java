package com.joaoandrade.traderexercicio.api.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joaoandrade.traderexercicio.api.model.RefreshTokenModel;
import com.joaoandrade.traderexercicio.domain.service.TokenService;

@RestController
@RequestMapping("/tokens")
public class TokenController {

	@Autowired
	private TokenService tokenService;

	@PutMapping("/refresh/{token}")
	public RefreshTokenModel refreshToken(@PathVariable String token, HttpServletResponse response) {
		String novoToken = tokenService.refreshToken(token);

		response.setHeader("Authorization", novoToken);
		return new RefreshTokenModel(token, novoToken);
	}
}
