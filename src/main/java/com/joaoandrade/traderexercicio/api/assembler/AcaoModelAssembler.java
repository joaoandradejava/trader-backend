package com.joaoandrade.traderexercicio.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.joaoandrade.traderexercicio.api.model.AcaoModel;
import com.joaoandrade.traderexercicio.domain.model.Acao;

@Component
public class AcaoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public AcaoModel toModel(Acao acao) {
		return modelMapper.map(acao, AcaoModel.class);
	}

	public List<AcaoModel> toCollectionModel(List<Acao> lista) {
		return lista.stream().map(acao -> toModel(acao)).collect(Collectors.toList());
	}
}
