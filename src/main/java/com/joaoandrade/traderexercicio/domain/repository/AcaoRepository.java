package com.joaoandrade.traderexercicio.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.joaoandrade.traderexercicio.domain.model.Acao;

@Repository
public interface AcaoRepository extends JpaRepository<Acao, Long> {

	public Optional<Acao> findByNome(String nome);

	@Query("select a from Acao a where a.nome like %?1%")
	public Page<Acao> buscarTodosPorPaginacaoENome(String nome, Pageable pageable);
}
