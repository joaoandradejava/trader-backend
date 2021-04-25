package com.joaoandrade.traderexercicio.core.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("jwt")
@Component
public class JwtConfig {

	/**
	 * Senha do token JWT
	 */
	private String senha;

	/**
	 * Tempo de expiração do token JWT em milisegundos
	 */
	private Long tempoExpiracao;

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Long getTempoExpiracao() {
		return tempoExpiracao;
	}

	public void setTempoExpiracao(Long tempoExpiracao) {
		this.tempoExpiracao = tempoExpiracao;
	}

}
