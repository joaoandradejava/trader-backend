package com.joaoandrade.traderexercicio.domain.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class ItemAcao {

	@EmbeddedId
	private ItemAcaoId id;
	private Integer quantidade;

	public ItemAcao() {
	}

	public ItemAcao(ItemAcaoId id, Integer quantidade) {
		super();
		this.id = id;
		this.quantidade = quantidade;
	}

	public ItemAcaoId getId() {
		return id;
	}

	public void setId(ItemAcaoId id) {
		this.id = id;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemAcao other = (ItemAcao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
