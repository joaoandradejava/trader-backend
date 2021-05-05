package com.joaoandrade.traderexercicio.domain.service.email;

import javax.mail.internet.MimeMessage;

import com.joaoandrade.traderexercicio.domain.model.Trader;

public class SMTPEnvioEmailService extends AbstractEnvioEmailService {

	@Override
	public void enviarEmail(Trader trader, String titulo, EmailObject corpo) {
		MimeMessage mimeMessage = prepararMimeMessage(trader, titulo, corpo);
		javaMailSender.send(mimeMessage);
	}

}
