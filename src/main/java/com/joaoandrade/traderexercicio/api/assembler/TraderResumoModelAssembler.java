package com.joaoandrade.traderexercicio.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.joaoandrade.traderexercicio.api.model.TraderResumoModel;
import com.joaoandrade.traderexercicio.domain.model.Trader;

@Component
public class TraderResumoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public TraderResumoModel toModel(Trader trader) {
		return modelMapper.map(trader, TraderResumoModel.class);
	}

	public List<TraderResumoModel> toCollectionModel(List<Trader> lista) {
		return lista.stream().map(trader -> toModel(trader)).collect(Collectors.toList());
	}
}
