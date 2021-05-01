package com.joaoandrade.traderexercicio.api.model;

public class RefreshTokenModel {
	private final String antigoToken;
	private final String novoToken;

	public RefreshTokenModel(String antigoToken, String novoToken) {
		this.antigoToken = antigoToken;
		this.novoToken = novoToken;
	}

	public String getAntigoToken() {
		return antigoToken;
	}

	public String getNovoToken() {
		return novoToken;
	}
}
