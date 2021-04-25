package com.joaoandrade.traderexercicio.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.joaoandrade.traderexercicio.api.model.TraderFullModel;
import com.joaoandrade.traderexercicio.domain.model.Trader;

@Component
public class TraderFullModelAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public TraderFullModel toModel(Trader trader) {
		return modelMapper.map(trader, TraderFullModel.class);
	}

	public List<TraderFullModel> toCollectionModel(List<Trader> lista) {
		return lista.stream().map(trader -> toModel(trader)).collect(Collectors.toList());
	}
}
