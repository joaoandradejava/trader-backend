package com.joaoandrade.traderexercicio.api.model;

public class ItemAcaoModel {
	private ItemAcaoIdModel id;
	private Integer quantidade;

	public ItemAcaoModel() {
	}

	public ItemAcaoIdModel getId() {
		return id;
	}

	public void setId(ItemAcaoIdModel id) {
		this.id = id;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

}
