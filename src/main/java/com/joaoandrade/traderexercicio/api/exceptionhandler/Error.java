package com.joaoandrade.traderexercicio.api.exceptionhandler;

public enum Error {
	NEGOCIO_EXCEPTION("negocio", "Erro do lado do Client-side(Front end)"),
	OBJETO_NAO_ENCONTRADO_EXCEPTION("objeto-nao-encontrado", "Objeto não Encontrado"),
	ENTIDADE_EM_USO_EXCEPTION("entidade-em-uso", "Entidade em Uso"),
	ERRO_DE_VALIDACAO("erro-validacao", "Erro de validação do dados"),
	METODO_NAO_SUPORTADO("metodo-nao-suportado", "Método não suportado"),
	NENHUM_RECURSO_ENCONTRADO("recurso-nao-encontrado", "Recurso não encontrado"),
	ERRO_DESSERIALIZACAO_JSON("erro-desserializacao-json", "Erro ao tentar desserializar o JSON"),
	PROPRIEDADE_INEXISTENTE("propriedade-inexistente", "Propriedade inexistente"),
	ERRO_INTERNO_NO_SERVIDOR("erro-interno-no-servidor", "Erro interno no servidor"),
	CREDENCIAIS_INCORRETAS("credenciais-incorretas", "Credenciais incorretas"),
	ACESSO_NEGADO("acesso-negado", "Acesso Negado");

	private String type;
	private String title;

	private Error(String type, String title) {
		this.type = "https://www.joaoandradejava.com.br/" + type;
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public String getTitle() {
		return title;
	}
}
