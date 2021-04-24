package com.joaoandrade.traderexercicio.domain.exception;

public class AcaoNaoEncontradaException extends ObjetoNaoEncontradoException {

	private static final long serialVersionUID = 1L;

	public AcaoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

	public AcaoNaoEncontradaException(Long id) {
		super(String.format("A Ação de id %d não foi encontrada no sistema!", id));
	}

}
