package com.joaoandrade.traderexercicio.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.joaoandrade.traderexercicio.domain.model.Trader;

@Repository
public interface TraderRepository extends JpaRepository<Trader, Long> {

	@Query("select t from Trader t where lower(t.nome) like lower(concat('%', ?1, '%'))")
	Page<Trader> buscarTodosPorPaginacaoENome(String nome, Pageable pageable);

	@Query("select t From Trader t where lower(t.email) like lower(concat('%', ?1, '%'))")
	Page<Trader> buscarTodosPorPaginacaoEEmail(String email, Pageable pageable);

	Optional<Trader> findByEmail(String email);

}
