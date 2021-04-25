package com.joaoandrade.traderexercicio.domain.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joaoandrade.traderexercicio.domain.exception.NegocioException;
import com.joaoandrade.traderexercicio.domain.model.Acao;
import com.joaoandrade.traderexercicio.domain.model.Trader;
import com.joaoandrade.traderexercicio.domain.service.crud.CadastroTraderService;

@Service
public class TraderService {

	@Autowired
	private CadastroTraderService cadastroTraderService;

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

	@Transactional
	public void alterarSenha(Long id, String senhaAtual, String novaSenha) {
		Trader trader = cadastroTraderService.buscarPorId(id);
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

		if (!bCryptPasswordEncoder.matches(senhaAtual, trader.getSenha())) {
			throw new NegocioException(String.format("Senha atual inserida não corresponde a sua senha!"));
		}

		trader.setSenha(bCryptPasswordEncoder.encode(novaSenha));
	}
}
