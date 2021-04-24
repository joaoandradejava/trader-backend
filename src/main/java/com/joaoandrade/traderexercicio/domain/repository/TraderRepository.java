package com.joaoandrade.traderexercicio.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joaoandrade.traderexercicio.domain.model.Trader;

@Repository
public interface TraderRepository extends JpaRepository<Trader, Long> {

}
