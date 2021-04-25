package com.joaoandrade.traderexercicio.api.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class MudarSenhaInput {

	@Size(max = 255)
	@NotBlank
	private String senhaAtual;

	@Size(max = 255)
	@NotBlank
	private String novaSenha;

	public MudarSenhaInput() {
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

}
