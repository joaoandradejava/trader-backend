package com.joaoandrade.traderexercicio.core.emailconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("email")
public class EmailConfigProperties {

	/**
	 * Email da conta do envio de email
	 */
	private String usuario;

	/**
	 * Senha da contaa do envio de email
	 */
	private String senha;

	/**
	 * Tipo de envio de email. SENDBOX - Para desenvolvimento, SMTP - Para produção
	 */
	private TipoEnvioEmail tipoEnvioEmail;

	/**
	 * Email de teste usado em desenvolvimento
	 */
	private String emailSendbox;

	public EmailConfigProperties() {
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public TipoEnvioEmail getTipoEnvioEmail() {
		return tipoEnvioEmail;
	}

	public void setTipoEnvioEmail(TipoEnvioEmail tipoEnvioEmail) {
		this.tipoEnvioEmail = tipoEnvioEmail;
	}

	public String getEmailSendbox() {
		return emailSendbox;
	}

	public void setEmailSendbox(String emailSendbox) {
		this.emailSendbox = emailSendbox;
	}

}
