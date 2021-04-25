package com.joaoandrade.traderexercicio.api.input;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class VenderAcaoInput {

	@NotNull
	private Long id;

	@NotNull
	@Positive
	private Integer quantidade;

	public VenderAcaoInput() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

}
