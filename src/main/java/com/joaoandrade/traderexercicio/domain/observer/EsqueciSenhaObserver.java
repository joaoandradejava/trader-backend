package com.joaoandrade.traderexercicio.domain.observer;

import com.joaoandrade.traderexercicio.domain.model.Trader;

public class EsqueciSenhaObserver {

	private final Trader trader;
	private final String novaSenha;

	public EsqueciSenhaObserver(Trader trader, String novaSenha) {
		this.trader = trader;
		this.novaSenha = novaSenha;
	}

	public Trader getTrader() {
		return trader;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

}
