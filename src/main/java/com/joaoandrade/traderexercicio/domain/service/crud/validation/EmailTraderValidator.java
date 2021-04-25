package com.joaoandrade.traderexercicio.domain.service.crud.validation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.joaoandrade.traderexercicio.domain.exception.NegocioException;
import com.joaoandrade.traderexercicio.domain.model.Trader;
import com.joaoandrade.traderexercicio.domain.repository.TraderRepository;

@Component
public class EmailTraderValidator {

	@Autowired
	private TraderRepository repository;

	public void validarEmail(Trader trader) {
		Optional<Trader> obj = repository.findByEmail(trader.getEmail());

		if (obj.isPresent() && !obj.get().equals(trader)) {
			throw new NegocioException(
					String.format("Já existe um Trader com o email '%s' cadastrado no sistema!", trader.getEmail()));
		}
	}
}
