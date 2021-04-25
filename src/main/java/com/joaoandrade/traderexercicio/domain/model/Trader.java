package com.joaoandrade.traderexercicio.domain.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.joaoandrade.traderexercicio.domain.model.enumeration.Perfil;

@Entity
public class Trader {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String email;
	private String senha;
	private BigDecimal saldo;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "perfil")
	@Enumerated(EnumType.STRING)
	private Set<Perfil> perfis = new HashSet<>();

	@OneToMany(mappedBy = "id.trader", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ItemAcao> acoes = new HashSet<>();

	public Trader() {
	}

	public Trader(Long id, String nome, String email, String senha, BigDecimal saldo) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.saldo = saldo;
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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public Set<Perfil> getPerfis() {
		return perfis;
	}

	public void setPerfis(Set<Perfil> perfis) {
		this.perfis = perfis;
	}

	public Set<ItemAcao> getAcoes() {
		return acoes;
	}

	public void setAcoes(Set<ItemAcao> acoes) {
		this.acoes = acoes;
	}

	public boolean isTemDinheiroSuficienteParaComprarAcao(Acao acao, Integer quantidade) {
		BigDecimal valorTotal = acao.getPreco().multiply(new BigDecimal(quantidade));

		return this.getSaldo().compareTo(valorTotal) >= 0;
	}

	public boolean isJaPossuiAcao(Acao acao) {
		for (ItemAcao item : getAcoes()) {
			if (item.getId().getAcao().equals(acao)) {
				return true;
			}
		}

		return false;
	}

	public void adicionarMaisQuantidadeAAcao(Acao acao, Integer quantidade) {
		for (ItemAcao item : getAcoes()) {
			if (item.getId().getAcao().equals(acao)) {
				item.adicionarQuantidade(quantidade);
				subtrairSaldo(acao.getPreco(), quantidade);
				break;
			}
		}

	}

	public void adicionarUmaNovaAcao(Acao acao, Integer quantidade) {
		ItemAcaoId id = new ItemAcaoId(this, acao);
		this.acoes.add(new ItemAcao(id, quantidade));
		subtrairSaldo(acao.getPreco(), quantidade);
	}

	private void subtrairSaldo(BigDecimal valor, Integer quantidade) {
		BigDecimal valorTotal = valor.multiply(new BigDecimal(quantidade));
		this.saldo = this.saldo.subtract(valorTotal);
	}

	private void somarSaldo(BigDecimal valor, Integer quantidade) {
		BigDecimal valorTotal = valor.multiply(new BigDecimal(quantidade));
		this.saldo = this.saldo.add(valorTotal);
	}

	public boolean isTemQuantidadeSuficienteParaPoderVender(Acao acao, Integer quantidade) {
		for (ItemAcao item : getAcoes()) {
			if (item.getId().getAcao().equals(acao)) {
				if (item.getQuantidade() >= quantidade) {
					return true;
				}
			}
		}

		return false;
	}

	public void venderAcoes(Acao acao, Integer quantidade) {
		for (ItemAcao item : getAcoes()) {
			if (item.getId().getAcao().equals(acao)) {
				item.subtrairQuantidade(quantidade);
				somarSaldo(acao.getPreco(), quantidade);
				if (item.getQuantidade() == 0) {
					removerItemAcao(item);
				}

				break;
			}
		}
	}

	private void removerItemAcao(ItemAcao item) {
		this.acoes.remove(item);
	}

	public boolean isTemDinheiroSuficienteParaTransferir(BigDecimal valor) {
		return this.saldo.compareTo(valor) >= 0;
	}

	public void transferirDinheiro(Trader destinatario, BigDecimal valor) {
		destinatario.somarSaldo(valor);
		this.subtrairSaldo(valor);

	}

	private void subtrairSaldo(BigDecimal valor) {
		this.saldo = this.saldo.subtract(valor);
	}

	private void somarSaldo(BigDecimal valor) {
		this.saldo = this.saldo.add(valor);
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
		Trader other = (Trader) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
