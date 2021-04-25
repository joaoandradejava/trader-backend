package com.joaoandrade.traderexercicio.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.joaoandrade.traderexercicio.api.input.TraderUpdateInput;
import com.joaoandrade.traderexercicio.domain.model.Trader;

@Component
public class TraderUpdateInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Trader toDomainModel(TraderUpdateInput traderUpdateInput) {
		return modelMapper.map(traderUpdateInput, Trader.class);
	}

	public void copyToDomainModel(TraderUpdateInput traderUpdateInput, Trader trader) {
		modelMapper.map(traderUpdateInput, trader);
	}
}
