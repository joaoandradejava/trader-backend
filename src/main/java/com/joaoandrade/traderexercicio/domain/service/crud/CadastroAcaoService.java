package com.joaoandrade.traderexercicio.domain.service.crud;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joaoandrade.traderexercicio.domain.exception.AcaoNaoEncontradaException;
import com.joaoandrade.traderexercicio.domain.exception.EntidadeEmUsoException;
import com.joaoandrade.traderexercicio.domain.model.Acao;
import com.joaoandrade.traderexercicio.domain.repository.AcaoRepository;
import com.joaoandrade.traderexercicio.domain.service.crud.validation.NomeAcaoValidator;

@Service
public class CadastroAcaoService {

	@Autowired
	private AcaoRepository repository;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private NomeAcaoValidator nomeAcaoValidator;

	public List<Acao> buscarTodos() {
		return repository.findAll();
	}

	public Page<Acao> buscarTodosPorPaginacao(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Page<Acao> buscarTodosPorPaginacaoENome(Pageable pageable, String nome) {
		return repository.buscarTodosPorPaginacaoENome(nome, pageable);
	}

	public Acao buscarPorId(Long id) {
		return repository.findById(id).orElseThrow(() -> new AcaoNaoEncontradaException(id));
	}

	@Transactional
	public Acao salvar(Acao acao) {
		entityManager.detach(acao);
		nomeAcaoValidator.validarNomeDaAcao(acao);

		return repository.save(acao);
	}

	@Transactional
	public void deletarPorId(Long id) {
		Acao acao = buscarPorId(id);

		try {
			repository.deleteById(id);
			repository.flush();
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String
					.format("Não foi possivel deletar a Ação '%s' pois ela estar em uso no sistema!", acao.getNome()));
		}
	}

}
