package com.joaoandrade.traderexercicio.core.jwt;

import java.io.IOException;
import java.time.OffsetDateTime;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joaoandrade.traderexercicio.api.exceptionhandler.Error;
import com.joaoandrade.traderexercicio.core.security.TraderAutenticado;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private JwtUtil jwtUtil;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
		setAuthenticationManager(authenticationManager);
		setAuthenticationFailureHandler(new FailureHandler());
		this.jwtUtil = jwtUtil;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			LoginDTO loginDTO = new ObjectMapper().readValue(request.getInputStream(), LoginDTO.class);

			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
					loginDTO.getEmail(), loginDTO.getSenha());

			return getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		TraderAutenticado traderAutenticado = (TraderAutenticado) authResult.getPrincipal();

		String token = "Bearer " + jwtUtil.gerarTokenJwt(traderAutenticado.getUsername());
		response.setHeader("Authorization", token);
		response.setContentType("application/json");
		response.getWriter().write(responseJson(traderAutenticado.getId(), token, traderAutenticado.isAdmin()));
	}

	private String responseJson(Long id, String token, boolean isAdmin) {
		StringBuilder builder = new StringBuilder();
		builder.append("{\n");
		builder.append("\"id\": \"" + id + "\",\n");
		builder.append("\"token\": \"" + token + "\",\n");
		builder.append("\"isAdmin\": \"" + isAdmin + "\"\n");
		builder.append("}");

		return builder.toString();
	}

	private class FailureHandler implements AuthenticationFailureHandler {

		@Override
		public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException exception) throws IOException, ServletException {
			response.setContentType("application/json");
			response.setStatus(401);
			response.getWriter().write(responseJson());

		}

		private String responseJson() {
			StringBuilder builder = new StringBuilder();
			Error error = Error.CREDENCIAIS_INCORRETAS;
			String message = "E-mail incorreto ou senha incorreta.";

			builder.append("{\n");
			builder.append("\"timestamp\": \"" + OffsetDateTime.now() + "\",\n");
			builder.append("\"type\": \"" + error.getType() + "\",\n");
			builder.append("\"title\": \"" + error.getTitle() + "\",\n");
			builder.append("\"status\": \"" + 401 + "\",\n");
			builder.append("\"detail\": \"" + message + "\",\n");
			builder.append("\"userMessage\": \"" + message + "\"\n");
			builder.append("}");

			return builder.toString();
		}
	}

}
