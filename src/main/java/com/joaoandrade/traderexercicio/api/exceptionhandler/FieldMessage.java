package com.joaoandrade.traderexercicio.api.exceptionhandler;

public class FieldMessage {
	private final String field;
	private final String userMessage;

	public FieldMessage(String field, String userMessage) {
		super();
		this.field = field;
		this.userMessage = userMessage;
	}

	public String getField() {
		return field;
	}

	public String getUserMessage() {
		return userMessage;
	}
}
