package com.joaoandrade.traderexercicio.api.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class TraderFullModel {
	private Long id;
	private String nome;
	private String email;
	private BigDecimal saldo;

	private Set<ItemAcaoModel> acoes = new HashSet<>();

	public TraderFullModel() {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public Set<ItemAcaoModel> getAcoes() {
		return acoes;
	}

	public void setAcoes(Set<ItemAcaoModel> acoes) {
		this.acoes = acoes;
	}

}
