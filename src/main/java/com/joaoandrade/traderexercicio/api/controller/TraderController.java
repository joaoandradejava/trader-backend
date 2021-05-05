package com.joaoandrade.traderexercicio.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

import com.joaoandrade.traderexercicio.api.assembler.TraderFullModelAssembler;
import com.joaoandrade.traderexercicio.api.assembler.TraderModelAssembler;
import com.joaoandrade.traderexercicio.api.assembler.TraderModelPaginationAssembler;
import com.joaoandrade.traderexercicio.api.assembler.TraderResumoModelAssembler;
import com.joaoandrade.traderexercicio.api.disassembler.TraderCreateInputDisassembler;
import com.joaoandrade.traderexercicio.api.disassembler.TraderUpdateInputDisassembler;
import com.joaoandrade.traderexercicio.api.input.ComprarAcaoInput;
import com.joaoandrade.traderexercicio.api.input.MudarSenhaInput;
import com.joaoandrade.traderexercicio.api.input.RecuperarSenhaInput;
import com.joaoandrade.traderexercicio.api.input.TraderCreateInput;
import com.joaoandrade.traderexercicio.api.input.TraderUpdateInput;
import com.joaoandrade.traderexercicio.api.input.TransferirDinheiroInput;
import com.joaoandrade.traderexercicio.api.input.VenderAcaoInput;
import com.joaoandrade.traderexercicio.api.model.TraderFullModel;
import com.joaoandrade.traderexercicio.api.model.TraderModel;
import com.joaoandrade.traderexercicio.api.model.TraderModelPagination;
import com.joaoandrade.traderexercicio.api.model.TraderResumoModel;
import com.joaoandrade.traderexercicio.core.security.TraderAutenticado;
import com.joaoandrade.traderexercicio.domain.exception.AcaoNaoEncontradaException;
import com.joaoandrade.traderexercicio.domain.exception.AcessoNegadoException;
import com.joaoandrade.traderexercicio.domain.exception.NegocioException;
import com.joaoandrade.traderexercicio.domain.exception.TraderNaoEncontradoException;
import com.joaoandrade.traderexercicio.domain.model.Acao;
import com.joaoandrade.traderexercicio.domain.model.Trader;
import com.joaoandrade.traderexercicio.domain.observer.EsqueciSenhaObserver;
import com.joaoandrade.traderexercicio.domain.service.TraderService;
import com.joaoandrade.traderexercicio.domain.service.crud.CadastroAcaoService;
import com.joaoandrade.traderexercicio.domain.service.crud.CadastroTraderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Trader Controller")
@RestController
@RequestMapping("/traders")
public class TraderController {

	@Autowired
	private TraderModelAssembler traderModelAssembler;

	@Autowired
	private TraderFullModelAssembler traderFullModelAssembler;

	@Autowired
	private CadastroTraderService cadastroTraderService;

	@Autowired
	private TraderCreateInputDisassembler traderCreateInputDisassembler;

	@Autowired
	private TraderUpdateInputDisassembler traderUpdateInputDisassembler;

	@Autowired
	private CadastroAcaoService cadastroAcaoService;

	@Autowired
	private TraderService traderService;

	@Autowired
	private TraderResumoModelAssembler traderResumoModelAssembler;

	@Autowired
	private TraderModelPaginationAssembler traderModelPaginationAssembler;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Operation(summary = "Buscar todos os traders", description = "Busca todos os traders do banco de dados. NIVEL DE ACESSO: ADMIN")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping
	public List<TraderModel> buscarTodos() {
		List<Trader> lista = cadastroTraderService.buscarTodos();

		return traderModelAssembler.toCollectionModel(lista);
	}

	@Operation(summary = "Buscar todos os traders por paginação", description = "Busca todos os traders por paginação do banco de dados. NIVEL DE ACESSO: ADMIN")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/paginacao")
	public Page<TraderModelPagination> buscarTodosPorPagintrader(Pageable pageable, String nome, String email) {
		Page<Trader> page;
		if (StringUtils.hasLength(nome)) {
			page = cadastroTraderService.buscarTodosPorPaginacaoENome(pageable, nome);

			return converterParaModelPage(page);
		}

		if (StringUtils.hasLength(email)) {
			page = cadastroTraderService.buscarTodosPorPaginacaoEEmail(pageable, email);

			return converterParaModelPage(page);
		}

		page = cadastroTraderService.buscarTodosPorPaginacao(pageable);
		return converterParaModelPage(page);
	}

	@Operation(summary = "Buscar trader por id", description = "Busca trader por id do banco de dados")
	@GetMapping("/{id}")
	public TraderFullModel buscarPorId(@PathVariable Long id,
			@AuthenticationPrincipal TraderAutenticado traderAutenticado) {
		if (isNaoTemAutorizacao(id, traderAutenticado)) {
			throw new AcessoNegadoException(
					String.format("Você não tem autorização para buscar os dados de outro Trader"));
		}

		Trader trader = cadastroTraderService.buscarPorId(id);

		return traderFullModelAssembler.toModel(trader);
	}

	@Operation(summary = "Buscar trader resumido por id", description = "Busca trader por id do banco de dados")
	@GetMapping("/resumo/{id}")
	public TraderResumoModel buscarResumoPorId(@PathVariable Long id,
			@AuthenticationPrincipal TraderAutenticado traderAutenticado) {
		if (isNaoTemAutorizacao(id, traderAutenticado)) {
			throw new AcessoNegadoException(
					String.format("Você não tem autorização para buscar os dados de outro Trader"));
		}

		Trader trader = cadastroTraderService.buscarPorId(id);

		return traderResumoModelAssembler.toModel(trader);
	}

	private boolean isNaoTemAutorizacao(Long id, TraderAutenticado traderAutenticado) {
		return traderAutenticado.getId() != id && !traderAutenticado.isAdmin();
	}

	@Operation(summary = "Salvar um novo Trader", description = "Salva um novo Trader no banco de dados")
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public TraderModel salvar(@Valid @RequestBody TraderCreateInput traderCreateInput) {
		Trader trader = cadastroTraderService.salvar(traderCreateInputDisassembler.toDomainModel(traderCreateInput));

		return traderModelAssembler.toModel(trader);
	}

	@Operation(summary = "Atualizar um Trader", description = "Atualiza um Trader no banco de dados")
	@PutMapping("/{id}")
	public TraderModel atualizar(@Valid @RequestBody TraderUpdateInput traderUpdateInput, @PathVariable Long id,
			@AuthenticationPrincipal TraderAutenticado traderAutenticado) {
		if (isNaoTemAutorizacao(id, traderAutenticado)) {
			throw new AcessoNegadoException(
					String.format("Você não tem autorização para atualizar os dados de outro Trader"));
		}

		Trader trader = cadastroTraderService.buscarPorId(id);
		traderUpdateInputDisassembler.copyToDomainModel(traderUpdateInput, trader);
		trader = cadastroTraderService.salvar(trader);

		return traderModelAssembler.toModel(trader);
	}

	@Operation(summary = "Deletar um Trader", description = "Deleta um Trader por id no banco de dados")
	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deletarPorId(@PathVariable Long id, @AuthenticationPrincipal TraderAutenticado traderAutenticado) {
		if (isNaoTemAutorizacao(id, traderAutenticado)) {
			throw new AcessoNegadoException(
					String.format("Você não tem autorização para deletar a conta de outro Trader"));
		}

		cadastroTraderService.deletarPorId(id);
	}

	@Operation(summary = "Comprar Ações", description = "Compra ações")
	@PutMapping("/{id}/acao")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void comprarAcao(@Valid @RequestBody ComprarAcaoInput comprarAcaoInput, @PathVariable Long id,
			@AuthenticationPrincipal TraderAutenticado traderAutenticado) {
		if (isNaoTemAutorizacao(id, traderAutenticado)) {
			throw new AcessoNegadoException(
					String.format("Você não tem autorização para comprar ações de outro Trader"));
		}

		try {
			Acao acao = cadastroAcaoService.buscarPorId(comprarAcaoInput.getId());
			Trader trader = cadastroTraderService.buscarPorId(id);
			traderService.comprarAcao(trader, acao, comprarAcaoInput.getQuantidade());
		} catch (AcaoNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@Operation(summary = "Vender ações", description = "Vende ações que o trader possui")
	@DeleteMapping("/{id}/acao")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void venderAcao(@Valid @RequestBody VenderAcaoInput venderAcaoInput, @PathVariable Long id,
			@AuthenticationPrincipal TraderAutenticado traderAutenticado) {
		if (isNaoTemAutorizacao(id, traderAutenticado)) {
			throw new AcessoNegadoException(
					String.format("Você não tem autorização para vender as ações de outro Trader"));
		}

		try {
			Acao acao = cadastroAcaoService.buscarPorId(venderAcaoInput.getId());
			Trader trader = cadastroTraderService.buscarPorId(id);
			traderService.venderAcao(trader, acao, venderAcaoInput.getQuantidade());
		} catch (AcaoNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@Operation(summary = "Transferir dinheiro", description = "Transferi o dinheiro para um outro Trader")
	@PutMapping("/{id}/transferencia-dinheiro")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void transferirDinheiro(@Valid @RequestBody TransferirDinheiroInput transferirDinheiroInput,
			@PathVariable Long id, @AuthenticationPrincipal TraderAutenticado traderAutenticado) {
		if (isNaoTemAutorizacao(id, traderAutenticado)) {
			throw new AcessoNegadoException(
					String.format("Você não tem autorização para transferir o dinheiro da conta de outro Trader"));
		}

		Trader trader = cadastroTraderService.buscarPorId(id);
		Trader destinatario;
		try {
			destinatario = cadastroTraderService.buscarPorEmail(transferirDinheiroInput.getEmailDestinatario());
		} catch (TraderNaoEncontradoException e) {
			throw new NegocioException(e.getMessage());
		}

		traderService.transferirDinheiro(trader, destinatario, transferirDinheiroInput.getValor());
	}

	@Operation(summary = "Mudar senha", description = "Muda a senha do trader logado no sistema")
	@PutMapping("/senha")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void altarSenha(@Valid @RequestBody MudarSenhaInput mudarSenhaInput,
			@AuthenticationPrincipal TraderAutenticado traderAutenticado) {
		traderService.alterarSenha(traderAutenticado.getId(), mudarSenhaInput.getSenhaAtual(),
				mudarSenhaInput.getNovaSenha());
	}

	@Operation(summary = "Dar permissão de Administrador", description = "Dar permissão de Administrador a um trader")
	@PutMapping("/{id}/admin")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAnyRole('ADMIN')")
	public void darPermissaoAdmin(@PathVariable Long id) {
		traderService.darPermissaoAdmin(id);
	}

	@Operation(summary = "Remove permissão de Administrador", description = "Remove permissão de Administrador de um trader")
	@DeleteMapping("/{id}/admin")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAnyRole('ADMIN')")
	public void removerPermissaoAdmin(@PathVariable Long id) {
		traderService.removerPermissaoAdmin(id);
	}

	@Operation(summary = "Recupera a senha esquecida", description = "Recupera a senha esquecida do trader")
	@PutMapping("/esqueci-senha")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void recuperarSenhaEsquecida(@Valid @RequestBody RecuperarSenhaInput recuperarSenhaInput) {
		EsqueciSenhaObserver esqueciSenhaObserver = traderService
				.recuperarSenhaEsquecida(recuperarSenhaInput.getEmail());

		applicationEventPublisher.publishEvent(esqueciSenhaObserver);
	}

	private Page<TraderModelPagination> converterParaModelPage(Page<Trader> page) {
		return page.map(trader -> traderModelPaginationAssembler.toModel(trader)

		);
	}
}
