package com.joaoandrade.traderexercicio.domain.service.crud.validation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.joaoandrade.traderexercicio.domain.exception.NegocioException;
import com.joaoandrade.traderexercicio.domain.model.Acao;
import com.joaoandrade.traderexercicio.domain.repository.AcaoRepository;

@Component
public class NomeAcaoValidator {

	@Autowired
	private AcaoRepository repository;

	public void validarNomeDaAcao(Acao acao) {
		Optional<Acao> obj = repository.findByNome(acao.getNome());

		if (obj.isPresent() && !obj.get().equals(acao)) {
			throw new NegocioException(
					String.format("Já existe uma Ação com o nome '%s' cadastrada no sistema!", acao.getNome()));
		}
	}

}
