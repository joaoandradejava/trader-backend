package com.joaoandrade.traderexercicio.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.joaoandrade.traderexercicio.api.input.AcaoInput;
import com.joaoandrade.traderexercicio.domain.model.Acao;

@Component
public class AcaoInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Acao toDomainModel(AcaoInput acaoInput) {
		return modelMapper.map(acaoInput, Acao.class);
	}

	public void copyToDomainModel(AcaoInput acaoInput, Acao acao) {
		modelMapper.map(acaoInput, acao);
	}
}
