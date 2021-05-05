package com.joaoandrade.traderexercicio.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.joaoandrade.traderexercicio.api.model.TraderModelPagination;
import com.joaoandrade.traderexercicio.domain.model.Trader;

@Component
public class TraderModelPaginationAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public TraderModelPagination toModel(Trader trader) {
		TraderModelPagination convertido = modelMapper.map(trader, TraderModelPagination.class);

		convertido.setIsAdmin(trader.isAdmin());

		return convertido;
	}

	public List<TraderModelPagination> toCollectionModel(List<Trader> lista) {
		return lista.stream().map(trader -> toModel(trader)).collect(Collectors.toList());
	}
}
