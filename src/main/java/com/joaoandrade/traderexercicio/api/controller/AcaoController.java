package com.joaoandrade.traderexercicio.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.joaoandrade.traderexercicio.api.assembler.AcaoModelAssembler;
import com.joaoandrade.traderexercicio.api.disassembler.AcaoInputDisassembler;
import com.joaoandrade.traderexercicio.api.input.AcaoInput;
import com.joaoandrade.traderexercicio.api.model.AcaoModel;
import com.joaoandrade.traderexercicio.domain.model.Acao;
import com.joaoandrade.traderexercicio.domain.service.crud.CadastroAcaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Acao Controller")
@RestController
@RequestMapping("/acoes")
public class AcaoController {

	@Autowired
	private AcaoModelAssembler acaoModelAssembler;

	@Autowired
	private CadastroAcaoService cadastroAcaoService;

	@Autowired
	private AcaoInputDisassembler acaoInputDisassembler;

	@Operation(summary = "Buscar todas as ações", description = "Busca todas as ações do banco de dados")
	@GetMapping
	public List<AcaoModel> buscarTodos() {
		List<Acao> lista = cadastroAcaoService.buscarTodos();

		return acaoModelAssembler.toCollectionModel(lista);
	}

	@Operation(summary = "Buscar todas as ações por paginação", description = "Busca todas as ações do banco de dados por paginação")
	@GetMapping("/paginacao")
	public Page<AcaoModel> buscarTodosPorPaginacao(Pageable pageable, String nome) {
		Page<Acao> page;
		if (StringUtils.hasLength(nome)) {
			page = cadastroAcaoService.buscarTodosPorPaginacaoENome(pageable, nome);

			return converterParaModelPage(page);
		}

		page = cadastroAcaoService.buscarTodosPorPaginacao(pageable);
		return converterParaModelPage(page);
	}

	private Page<AcaoModel> converterParaModelPage(Page<Acao> page) {
		return page.map(acao -> acaoModelAssembler.toModel(acao));
	}

	@Operation(summary = "Buscar uma ação por id", description = "Busca uma ação do banco de dados por id")
	@GetMapping("/{id}")
	public AcaoModel buscarPorId(@PathVariable Long id) {
		Acao acao = cadastroAcaoService.buscarPorId(id);

		return acaoModelAssembler.toModel(acao);
	}

	@Operation(summary = "Salva uma nova ação", description = "Salva uma nova ação no banco de dados. NIVEL DE ACESSO: ADMIN")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public AcaoModel salvar(@Valid @RequestBody AcaoInput acaoInput) {
		Acao acao = cadastroAcaoService.salvar(acaoInputDisassembler.toDomainModel(acaoInput));

		return acaoModelAssembler.toModel(acao);
	}

	@Operation(summary = "Atualiza uma ação já existente", description = "Atualiza uma ação já existente no banco de dados. NIVEL DE ACESSO: ADMIN")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping("/{id}")
	public AcaoModel atualizar(@Valid @RequestBody AcaoInput acaoInput, @PathVariable Long id) {
		Acao acao = cadastroAcaoService.buscarPorId(id);
		acaoInputDisassembler.copyToDomainModel(acaoInput, acao);
		acao = cadastroAcaoService.salvar(acao);

		return acaoModelAssembler.toModel(acao);
	}

	@Operation(summary = "Deleta uma ação por id", description = "Deleta uma ação por id do banco de dados. NIVEL DE ACESSO: ADMIN")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deletarPorId(@PathVariable Long id) {
		cadastroAcaoService.deletarPorId(id);
	}
}
