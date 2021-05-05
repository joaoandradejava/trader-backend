package com.joaoandrade.traderexercicio.api.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RecuperarSenhaInput {

	@NotBlank
	@Size(max = 255)
	@Email
	private String email;

	public RecuperarSenhaInput() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
