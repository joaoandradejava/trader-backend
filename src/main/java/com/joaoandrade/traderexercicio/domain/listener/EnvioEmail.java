package com.joaoandrade.traderexercicio.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.joaoandrade.traderexercicio.domain.observer.EsqueciSenhaObserver;
import com.joaoandrade.traderexercicio.domain.service.email.EmailObject;
import com.joaoandrade.traderexercicio.domain.service.email.EnvioEmailService;

@Component
public class EnvioEmail {

	@Autowired
	private EnvioEmailService envioEmailService;

	@EventListener
	public void esqueciSenhaListener(EsqueciSenhaObserver esqueciSenhaObserver) {
		EmailObject emailObject = new EmailObject("recuperar-senha.html", esqueciSenhaObserver);

		envioEmailService.enviarEmail(esqueciSenhaObserver.getTrader(), "Recuperação da senha", emailObject);

	}
}
