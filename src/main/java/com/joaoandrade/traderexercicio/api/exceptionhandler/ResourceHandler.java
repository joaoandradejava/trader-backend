package com.joaoandrade.traderexercicio.api.exceptionhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.joaoandrade.traderexercicio.domain.exception.AcessoNegadoException;
import com.joaoandrade.traderexercicio.domain.exception.EntidadeEmUsoException;
import com.joaoandrade.traderexercicio.domain.exception.NegocioException;
import com.joaoandrade.traderexercicio.domain.exception.ObjetoNaoEncontradoException;

@ControllerAdvice
public class ResourceHandler extends ResponseEntityExceptionHandler {
	private static final String DEFAULT_MESSAGE = "Ocorreu um erro inesperado, se o problema persistir recomendo que entre em contato com o desenvolvedor da API.";

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleErroInternoNoServidor(Exception ex, WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		Error error = Error.ERRO_INTERNO_NO_SERVIDOR;
		ProblemDetail problemDetail = new ProblemDetail(error.getType(), error.getTitle(), status.value(),
				DEFAULT_MESSAGE, DEFAULT_MESSAGE);

		return handleExceptionInternal(ex, problemDetail, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Object> handleAccessDenied(AccessDeniedException ex, WebRequest request) {
		HttpStatus status = HttpStatus.FORBIDDEN;
		Error error = Error.ACESSO_NEGADO;
		String message = "Acesso negado!";
		ProblemDetail problemDetail = new ProblemDetail(error.getType(), error.getTitle(), status.value(), message,
				message);

		return handleExceptionInternal(ex, problemDetail, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(AcessoNegadoException.class)
	public ResponseEntity<Object> handleAcessoNegado(AcessoNegadoException ex, WebRequest request) {
		HttpStatus status = HttpStatus.FORBIDDEN;
		Error error = Error.ACESSO_NEGADO;
		String message = ex.getMessage();
		ProblemDetail problemDetail = new ProblemDetail(error.getType(), error.getTitle(), status.value(), message,
				message);

		return handleExceptionInternal(ex, problemDetail, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<Object> handleNegocio(NegocioException ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Error error = Error.NEGOCIO_EXCEPTION;
		ProblemDetail problemDetail = new ProblemDetail(error.getType(), error.getTitle(), status.value(),
				ex.getMessage(), ex.getMessage());

		return handleExceptionInternal(ex, problemDetail, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(ObjetoNaoEncontradoException.class)
	public ResponseEntity<Object> handleObjetoNaoEncontrado(ObjetoNaoEncontradoException ex, WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		Error error = Error.OBJETO_NAO_ENCONTRADO_EXCEPTION;
		ProblemDetail problemDetail = new ProblemDetail(error.getType(), error.getTitle(), status.value(),
				ex.getMessage(), ex.getMessage());

		return handleExceptionInternal(ex, problemDetail, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<Object> handleEntidadeEmUso(EntidadeEmUsoException ex, WebRequest request) {
		HttpStatus status = HttpStatus.CONFLICT;
		Error error = Error.ENTIDADE_EM_USO_EXCEPTION;
		ProblemDetail problemDetail = new ProblemDetail(error.getType(), error.getTitle(), status.value(),
				ex.getMessage(), ex.getMessage());

		return handleExceptionInternal(ex, problemDetail, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		Error error = Error.NENHUM_RECURSO_ENCONTRADO;
		String message = String.format("N??o existe nenhum endpoint mapeado para %s%s", ex.getHttpMethod(),
				ex.getRequestURL());
		ProblemDetail problemDetail = new ProblemDetail(error.getType(), error.getTitle(), status.value(), message,
				DEFAULT_MESSAGE);

		return handleExceptionInternal(ex, problemDetail, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Throwable cause = ex.getCause();

		if (cause instanceof UnrecognizedPropertyException) {
			return handleUnrecognizedProperty((UnrecognizedPropertyException) cause, headers, status, request);
		}

		Error error = Error.ERRO_DESSERIALIZACAO_JSON;
		String message = "Erro ao tentar fazer a desserializa????o do JSON. Verifique se a sintaxe do JSON foi inserida corretamente no corpo da requisi????o.";
		ProblemDetail problemDetail = new ProblemDetail(error.getType(), error.getTitle(), status.value(), message,
				DEFAULT_MESSAGE);

		return handleExceptionInternal(ex, problemDetail, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Error error = Error.METODO_NAO_SUPORTADO;
		String message = String.format("A Requisi????o feita para o M??todo '%s' n??o ?? suportada!", ex.getMethod());
		ProblemDetail problemDetail = new ProblemDetail(error.getType(), error.getTitle(), status.value(), message,
				DEFAULT_MESSAGE);

		return handleExceptionInternal(ex, problemDetail, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Error error = Error.ERRO_DE_VALIDACAO;
		String message = "Ocorreu um erro de valida????o dos dados. Verifique se os dados foram inseridos corretamente no corpo daa requisi????o";
		ProblemDetail problemDetail = new ProblemDetail(error.getType(), error.getTitle(), status.value(), message,
				message);

		for (ObjectError objectError : ex.getAllErrors()) {
			String field = objectError.getObjectName();

			if (objectError instanceof FieldError) {
				field = ((FieldError) objectError).getField();
			}

			String userMessage = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
			problemDetail.adicionarError(field, userMessage);
		}

		return handleExceptionInternal(ex, problemDetail, headers, status, request);
	}

	private ResponseEntity<Object> handleUnrecognizedProperty(UnrecognizedPropertyException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		Error error = Error.PROPRIEDADE_INEXISTENTE;
		String message = String.format("A Propriedade '%s' inserida no corpo da requisi????o ?? inexistente!",
				ex.getPropertyName());
		ProblemDetail problemDetail = new ProblemDetail(error.getType(), error.getTitle(), status.value(), message,
				DEFAULT_MESSAGE);

		return handleExceptionInternal(ex, problemDetail, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		if (body == null) {
			body = new ProblemDetail(null, null, status.value(), status.getReasonPhrase(), DEFAULT_MESSAGE);
		}

		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
}
