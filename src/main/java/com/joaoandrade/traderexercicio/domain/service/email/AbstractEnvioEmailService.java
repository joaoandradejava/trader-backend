package com.joaoandrade.traderexercicio.domain.service.email;

import java.io.IOException;
import java.util.Date;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.joaoandrade.traderexercicio.core.emailconfig.EmailConfigProperties;
import com.joaoandrade.traderexercicio.domain.exception.NegocioException;
import com.joaoandrade.traderexercicio.domain.model.Trader;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public abstract class AbstractEnvioEmailService implements EnvioEmailService {

	@Autowired
	protected JavaMailSender javaMailSender;

	@Autowired
	protected EmailConfigProperties emailConfigProperties;

	@Autowired
	private Configuration configuration;

	protected MimeMessage prepararMimeMessage(Trader trader, String titulo, EmailObject corpo) {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
			helper.setSentDate(new Date());
			helper.setFrom(emailConfigProperties.getUsuario());
			helper.setTo(trader.getEmail());
			helper.setSubject(titulo);
			helper.setText(getTemplateHtmlString(corpo.getCorpo(), corpo.getModel()), true);

			return mimeMessage;
		} catch (Exception e) {
			throw new NegocioException("Ocorreu um erro inesperado ao tentar enviar o email");
		}
	}

	private String getTemplateHtmlString(String corpo, Object model) throws IOException, TemplateException {
		Template template = configuration.getTemplate(corpo, LocaleContextHolder.getLocale(), "UTF-8");

		return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
	}
}
