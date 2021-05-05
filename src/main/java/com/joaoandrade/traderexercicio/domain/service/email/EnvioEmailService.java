package com.joaoandrade.traderexercicio.domain.service.email;

import com.joaoandrade.traderexercicio.domain.model.Trader;

public interface EnvioEmailService {

	public void enviarEmail(Trader trader, String titulo, EmailObject corpo);
}
