package com.joaoandrade.traderexercicio.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
import com.joaoandrade.traderexercicio.api.disassembler.TraderCreateInputDisassembler;
import com.joaoandrade.traderexercicio.api.disassembler.TraderUpdateInputDisassembler;
import com.joaoandrade.traderexercicio.api.input.ComprarAcaoInput;
import com.joaoandrade.traderexercicio.api.input.TraderCreateInput;
import com.joaoandrade.traderexercicio.api.input.TraderUpdateInput;
import com.joaoandrade.traderexercicio.api.input.TransferirDinheiroInput;
import com.joaoandrade.traderexercicio.api.input.VenderAcaoInput;
import com.joaoandrade.traderexercicio.api.model.TraderFullModel;
import com.joaoandrade.traderexercicio.api.model.TraderModel;
import com.joaoandrade.traderexercicio.domain.exception.AcaoNaoEncontradaException;
import com.joaoandrade.traderexercicio.domain.exception.NegocioException;
import com.joaoandrade.traderexercicio.domain.exception.TraderNaoEncontradoException;
import com.joaoandrade.traderexercicio.domain.model.Acao;
import com.joaoandrade.traderexercicio.domain.model.Trader;
import com.joaoandrade.traderexercicio.domain.service.TraderService;
import com.joaoandrade.traderexercicio.domain.service.crud.CadastroAcaoService;
import com.joaoandrade.traderexercicio.domain.service.crud.CadastroTraderService;

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

	@GetMapping
	public List<TraderModel> buscarTodos() {
		List<Trader> lista = cadastroTraderService.buscarTodos();

		return traderModelAssembler.toCollectionModel(lista);
	}

	@GetMapping("/paginacao")
	public Page<TraderModel> buscarTodosPorPagintrader(Pageable pageable, String nome, String email) {
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

	@GetMapping("/{id}")
	public TraderFullModel buscarPorId(@PathVariable Long id) {
		Trader trader = cadastroTraderService.buscarPorId(id);

		return traderFullModelAssembler.toModel(trader);
	}

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public TraderModel salvar(@Valid @RequestBody TraderCreateInput traderCreateInput) {
		Trader trader = cadastroTraderService.salvar(traderCreateInputDisassembler.toDomainModel(traderCreateInput));

		return traderModelAssembler.toModel(trader);
	}

	@PutMapping("/{id}")
	public TraderModel atualizar(@Valid @RequestBody TraderUpdateInput traderUpdateInput, @PathVariable Long id) {
		Trader trader = cadastroTraderService.buscarPorId(id);
		traderUpdateInputDisassembler.copyToDomainModel(traderUpdateInput, trader);
		trader = cadastroTraderService.salvar(trader);

		return traderModelAssembler.toModel(trader);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deletarPorId(@PathVariable Long id) {
		cadastroTraderService.deletarPorId(id);
	}

	@PutMapping("/{id}/acao")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void comprarAcao(@Valid @RequestBody ComprarAcaoInput comprarAcaoInput, @PathVariable Long id) {
		try {
			Acao acao = cadastroAcaoService.buscarPorId(comprarAcaoInput.getId());
			Trader trader = cadastroTraderService.buscarPorId(id);
			traderService.comprarAcao(trader, acao, comprarAcaoInput.getQuantidade());
		} catch (AcaoNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@DeleteMapping("/{id}/acao")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void venderAcao(@Valid @RequestBody VenderAcaoInput venderAcaoInput, @PathVariable Long id) {
		try {
			Acao acao = cadastroAcaoService.buscarPorId(venderAcaoInput.getId());
			Trader trader = cadastroTraderService.buscarPorId(id);
			traderService.venderAcao(trader, acao, venderAcaoInput.getQuantidade());
		} catch (AcaoNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@PutMapping("/{id}/transferencia-dinheiro")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void transferirDinheiro(@Valid @RequestBody TransferirDinheiroInput transferirDinheiroInput,
			@PathVariable Long id) {
		Trader trader = cadastroTraderService.buscarPorId(id);
		Trader destinatario;
		try {
			destinatario = cadastroTraderService.buscarPorEmail(transferirDinheiroInput.getEmailDestinatario());
		} catch (TraderNaoEncontradoException e) {
			throw new NegocioException(e.getMessage());
		}

		traderService.transferirDinheiro(trader, destinatario, transferirDinheiroInput.getValor());
	}

	private Page<TraderModel> converterParaModelPage(Page<Trader> page) {
		return page.map(trader -> traderModelAssembler.toModel(trader));
	}
}
