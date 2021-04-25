package com.joaoandrade.traderexercicio.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.joaoandrade.traderexercicio.api.input.TraderCreateInput;
import com.joaoandrade.traderexercicio.domain.model.Trader;

@Component
public class TraderCreateInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Trader toDomainModel(TraderCreateInput traderCreateInput) {
		return modelMapper.map(traderCreateInput, Trader.class);
	}

	public void copyToDomainModel(TraderCreateInput traderCreateInput, Trader trader) {
		modelMapper.map(traderCreateInput, trader);
	}
}
