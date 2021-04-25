package com.joaoandrade.traderexercicio.domain.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joaoandrade.traderexercicio.domain.exception.NegocioException;
import com.joaoandrade.traderexercicio.domain.model.Acao;
import com.joaoandrade.traderexercicio.domain.model.Trader;

@Service
public class TraderService {

	@Transactional
	public void comprarAcao(Trader trader, Acao acao, Integer quantidade) {
		if (!trader.isTemDinheiroSuficienteParaComprarAcao(acao, quantidade)) {
			throw new NegocioException("Dinheiro insuficiente para comprar as ações!");
		}

		if (trader.isJaPossuiAcao(acao)) {
			trader.adicionarMaisQuantidadeAAcao(acao, quantidade);
		} else {
			trader.adicionarUmaNovaAcao(acao, quantidade);
		}

	}

	@Transactional
	public void venderAcao(Trader trader, Acao acao, Integer quantidade) {
		if (!trader.isJaPossuiAcao(acao)) {
			throw new NegocioException(String.format("Não foi possivel vender a ação, pois %s não tem a Ação %s",
					trader.getNome(), acao.getNome()));
		}

		if (!trader.isTemQuantidadeSuficienteParaPoderVender(acao, quantidade)) {
			throw new NegocioException(String.format("Você não possui quantidade de ações suficiente para vender"));

		}

		trader.venderAcoes(acao, quantidade);
	}

	@Transactional
	public void transferirDinheiro(Trader trader, Trader destinatario, BigDecimal valor) {
		if (!trader.isTemDinheiroSuficienteParaTransferir(valor)) {
			throw new NegocioException(String.format(
					"Não foi possivel fazer a transferencia %s, pois você não tem saldo suficiente na sua conta!",
					trader.getNome()));
		}

		trader.transferirDinheiro(destinatario, valor);
	}
}
