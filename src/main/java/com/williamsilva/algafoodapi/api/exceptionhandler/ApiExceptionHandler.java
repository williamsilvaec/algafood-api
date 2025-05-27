package com.williamsilva.algafoodapi.api.exceptionhandler;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.williamsilva.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.williamsilva.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.williamsilva.algafoodapi.domain.exception.NegocioException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<Object> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemaTipo problemaTipo = ProblemaTipo.ENTIDADE_NAO_ENCONTRADA;
        String detalhe = ex.getMessage();

        Problema problema = criarProblemaBuilder(status, problemaTipo, detalhe).build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<Object> handleEntidadeEmUso(EntidadeEmUsoException ex, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        ProblemaTipo problemaTipo = ProblemaTipo.RECURSO_EM_USO;
        String detalhe = ex.getMessage();

        Problema problema = criarProblemaBuilder(status, problemaTipo, detalhe).build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Object> handleNegocio(NegocioException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemaTipo problemaTipo = ProblemaTipo.ERRO_NEGOCIO;
        String detalhe = ex.getMessage();

        Problema problema = criarProblemaBuilder(status, problemaTipo, detalhe).build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemaTipo problemaTipo = ProblemaTipo.ERRO_SISTEMA;
        String detalhe = "Ocorreu um erro interno inesperado no sistema. "
                + "Tente novamente e se o problema persistir, entre em contato "
                + "com o administrador do sistema.";

        ex.printStackTrace();

        Problema problema = criarProblemaBuilder(status, problemaTipo, detalhe).build();
        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {

        Throwable causaRaiz = ExceptionUtils.getRootCause(ex);
        if (causaRaiz instanceof InvalidFormatException invalidFormatException) {
            return handleInvalidFormat(invalidFormatException, headers, status, request);
        }

        if (causaRaiz instanceof PropertyBindingException propertyBindingException) {
            return handlePropertyBinding(propertyBindingException, headers, status, request);
        }

        ProblemaTipo problemaTipo = ProblemaTipo.MENSAGEM_INCOMPREENSIVEL;
        String detalhe = "O corpo da requisição está inválido. Verifique erro de sintaxe.";

        Problema problema = criarProblemaBuilder(status, problemaTipo, detalhe).build();

        return handleExceptionInternal(ex, problema, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {

        ProblemaTipo problemaTipo = ProblemaTipo.RECURSO_EM_USO;
        String detalhe = String.format("O recurso %s, que você tentou acessar, é inexistente.",
                ex.getRequestURL());

        Problema problema = criarProblemaBuilder(status, problemaTipo, detalhe).build();

        return handleExceptionInternal(ex, problema, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex,
                                                         HttpHeaders headers, HttpStatus status,
                                                         WebRequest request) {
        ProblemaTipo problemaTipo = ProblemaTipo.MENSAGEM_INCOMPREENSIVEL;
        String detalhe = String.format("A propriedade '%s' não existe. "
                + "Corrija ou remova essa propriedade e tente novamente.", getNomePropriedade(ex.getPath()));

        Problema problema = criarProblemaBuilder(status, problemaTipo, detalhe).build();

        return handleExceptionInternal(ex, problema, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex,
                                                       HttpHeaders headers, HttpStatus status,
                                                       WebRequest request) {

        String nomePropriedade = getNomePropriedade(ex.getPath());

        ProblemaTipo problemaTipo = ProblemaTipo.MENSAGEM_INCOMPREENSIVEL;
        String detalhe = String.format("A propriedade '%s' recebeu o valor '%s', que é inválido. " +
                        "Corrija e informe um valor compatível com o tipo %s.",
                nomePropriedade, ex.getValue(), ex.getTargetType().getSimpleName());

        Problema problema = criarProblemaBuilder(status, problemaTipo, detalhe).build();

        return handleExceptionInternal(ex, problema, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {

        if (body == null) {
            body = new Problema.Builder()
                    .titulo(status.getReasonPhrase())
                    .status(status.value())
                    .build();
        } else if (body instanceof String bodyString) {
            body = new Problema.Builder()
                    .titulo(bodyString)
                    .status(status.value())
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                        HttpStatus status, WebRequest request) {

        if (ex instanceof MethodArgumentTypeMismatchException methodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch(methodArgumentTypeMismatchException, headers, status, request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    private Problema.Builder criarProblemaBuilder(HttpStatus status, ProblemaTipo problemaTipo, String detalhe) {
        return new Problema.Builder()
                .status(status.value())
                .tipo(problemaTipo.getUri())
                .titulo(problemaTipo.getTitulo())
                .detalhe(detalhe);
    }

    private String getNomePropriedade(List<Reference> references) {
        return references.stream()
                .map(Reference::getFieldName)
                .collect(Collectors.joining("."));
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        ProblemaTipo problemaTipo = ProblemaTipo.PARAMETRO_INVALIDO;
        Class<?> requiredType = ex.getRequiredType();

        String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', "
                        + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
                ex.getName(), ex.getValue(), requiredType != null ? requiredType.getSimpleName() : "N/A");

        Problema problem = criarProblemaBuilder(status, problemaTipo, detail).build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }
}
