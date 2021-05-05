package com.joaoandrade.traderexercicio.domain.service.email;

public class EmailObject {
	private final String corpo;
	private final Object model;

	public EmailObject(String corpo, Object model) {
		this.corpo = corpo;
		this.model = model;
	}

	public String getCorpo() {
		return corpo;
	}

	public Object getModel() {
		return model;
	}

}
