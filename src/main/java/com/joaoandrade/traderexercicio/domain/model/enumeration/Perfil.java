package com.joaoandrade.traderexercicio.domain.model.enumeration;

public enum Perfil {
	TRADER("ROLE_TRADER"), ADMIN("ROLE_ADMIN");

	private String descricao;

	private Perfil(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
