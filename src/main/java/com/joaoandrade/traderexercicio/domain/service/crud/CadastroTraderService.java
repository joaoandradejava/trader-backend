package com.joaoandrade.traderexercicio.domain.service.crud;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joaoandrade.traderexercicio.domain.exception.TraderNaoEncontradoException;
import com.joaoandrade.traderexercicio.domain.exception.EntidadeEmUsoException;
import com.joaoandrade.traderexercicio.domain.model.Trader;
import com.joaoandrade.traderexercicio.domain.repository.TraderRepository;
import com.joaoandrade.traderexercicio.domain.service.crud.validation.EmailTraderValidator;

@Service
public class CadastroTraderService {

	@Autowired
	private TraderRepository repository;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private EmailTraderValidator emailTraderValidator;

	public List<Trader> buscarTodos() {
		return repository.findAll();
	}

	public Page<Trader> buscarTodosPorPaginacao(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Page<Trader> buscarTodosPorPaginacaoENome(Pageable pageable, String nome) {
		return repository.buscarTodosPorPaginacaoENome(nome, pageable);
	}

	public Page<Trader> buscarTodosPorPaginacaoEEmail(Pageable pageable, String email) {
		return repository.buscarTodosPorPaginacaoEEmail(email, pageable);
	}

	public Trader buscarPorId(Long id) {
		return repository.findById(id).orElseThrow(() -> new TraderNaoEncontradoException(id));
	}

	public Trader buscarPorEmail(String email) {
		return repository.findByEmail(email).orElseThrow(() -> new TraderNaoEncontradoException(
				String.format("O Trader de Email %s não foi encontrado no sistema!", email)));
	}

	@Transactional
	public Trader salvar(Trader trader) {
		entityManager.detach(trader);
		if (trader.getId() == null) {
			trader.setSaldo(new BigDecimal(0.0));
		}
		emailTraderValidator.validarEmail(trader);

		return repository.save(trader);
	}

	@Transactional
	public void deletarPorId(Long id) {
		Trader trader = buscarPorId(id);

		try {
			repository.deleteById(id);
			repository.flush();
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(
					"Não foi possivel deletar o Trader '%s' pois ele estar em uso no sistema!", trader.getNome()));
		}
	}

}
