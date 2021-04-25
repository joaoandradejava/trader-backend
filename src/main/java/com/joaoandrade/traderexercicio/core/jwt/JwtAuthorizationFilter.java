package com.joaoandrade.traderexercicio.core.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	private JwtUtil jwtUtil;
	private UserDetailsService userDetailsService;

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
			UserDetailsService userDetailsService) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String token = request.getHeader("Authorization");

		if (StringUtils.hasLength(token) && token.startsWith("Bearer ")) {
			token = token.replaceAll("Bearer ", "");
			if (jwtUtil.isTokenValido(token)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = getAuthentication(token);

				if (usernamePasswordAuthenticationToken != null) {
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}

		}

		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(String token) {
		String subject = jwtUtil.getSubject(token);

		if (StringUtils.hasLength(subject)) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(subject);

			return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		}

		return null;
	}

}
