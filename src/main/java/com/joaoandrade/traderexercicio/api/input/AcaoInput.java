package com.joaoandrade.traderexercicio.api.input;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class AcaoInput {

	@Size(min = 2, max = 255)
	@NotBlank
	private String nome;

	@NotNull
	@Positive
	private BigDecimal preco;

	public AcaoInput() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

}
