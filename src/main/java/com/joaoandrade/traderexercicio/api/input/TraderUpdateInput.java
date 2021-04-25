package com.joaoandrade.traderexercicio.api.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TraderUpdateInput {

	@Size(min = 3, max = 255)
	@NotBlank
	private String nome;

	public TraderUpdateInput() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
