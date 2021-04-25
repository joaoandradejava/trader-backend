package com.joaoandrade.traderexercicio.api.input;

import java.math.BigDecimal;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class TransferirDinheiroInput {

	@NotNull
	@Email
	@Size(max = 255)
	private String emailDestinatario;

	@NotNull
	@Positive
	private BigDecimal valor;

	public TransferirDinheiroInput() {
	}

	public String getEmailDestinatario() {
		return emailDestinatario;
	}

	public void setEmailDestinatario(String emailDestinatario) {
		this.emailDestinatario = emailDestinatario;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

}
