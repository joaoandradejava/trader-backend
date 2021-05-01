package com.joaoandrade.traderexercicio.api.model;

import java.math.BigDecimal;

public class TraderResumoModel {
	private Long id;
	private String nome;
	private BigDecimal saldo;

	public TraderResumoModel() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

}
