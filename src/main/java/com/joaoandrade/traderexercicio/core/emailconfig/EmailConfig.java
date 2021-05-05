package com.joaoandrade.traderexercicio.core.emailconfig;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.joaoandrade.traderexercicio.domain.service.email.EnvioEmailService;
import com.joaoandrade.traderexercicio.domain.service.email.SMTPEnvioEmailService;
import com.joaoandrade.traderexercicio.domain.service.email.SendboxEnvioEmailService;

@Configuration
public class EmailConfig {

	@Autowired
	private EmailConfigProperties emailConfigProperties;

	@Bean
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);

		mailSender.setUsername(emailConfigProperties.getUsuario());
		mailSender.setPassword("famossisimaJinx");

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");

		return mailSender;
	}

	@Bean
	public EnvioEmailService envioEmailService() {
		if (emailConfigProperties.getTipoEnvioEmail() == null
				|| emailConfigProperties.getTipoEnvioEmail() == TipoEnvioEmail.SMTP) {
			return new SMTPEnvioEmailService();
		}

		return new SendboxEnvioEmailService();
	}
}
