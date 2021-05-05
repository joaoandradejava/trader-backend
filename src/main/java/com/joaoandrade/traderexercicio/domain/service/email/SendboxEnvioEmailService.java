package com.joaoandrade.traderexercicio.domain.service.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.MimeMessageHelper;

import com.joaoandrade.traderexercicio.domain.exception.NegocioException;
import com.joaoandrade.traderexercicio.domain.model.Trader;

public class SendboxEnvioEmailService extends AbstractEnvioEmailService {

	@Override
	public void enviarEmail(Trader trader, String titulo, EmailObject corpo) {
		try {
			MimeMessage mimeMessage = prepararMimeMessage(trader, titulo, corpo);
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
			helper.setTo(emailConfigProperties.getEmailSendbox());
			javaMailSender.send(mimeMessage);
		} catch (MessagingException e) {
			throw new NegocioException("Ocorreu um erro ao tentar enviar o email");
		}

	}

}
