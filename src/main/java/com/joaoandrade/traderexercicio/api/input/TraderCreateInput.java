package com.joaoandrade.traderexercicio.api.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TraderCreateInput {

	@Size(min = 3, max = 255)
	@NotBlank
	private String nome;

	@Size(max = 255)
	@Email
	@NotBlank
	private String email;

	@Size(max = 255)
	@NotBlank
	private String senha;

	public TraderCreateInput() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
