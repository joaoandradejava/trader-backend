package com.joaoandrade.traderexercicio.domain.exception;

public class TraderNaoEncontradoException extends ObjetoNaoEncontradoException {

	private static final long serialVersionUID = 1L;

	public TraderNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public TraderNaoEncontradoException(Long id) {
		super(String.format("O Trader de id %d não foi encontrado no sistema!", id));
	}

}
