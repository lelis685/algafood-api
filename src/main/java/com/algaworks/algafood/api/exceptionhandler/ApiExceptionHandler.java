package com.algaworks.algafood.api.exceptionhandler;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
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
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex,
                                                                  WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        Problem body = createProblemBuilder(status, ProblemType.ENTIDADE_NAO_ENCONTRADA, ex.getMessage()).build();

        return handleExceptionInternal(ex, body, new HttpHeaders(), status, request);
    }


    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocio(NegocioException ex, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        Problem body = createProblemBuilder(status, ProblemType.ERRO_NEGOCIO, ex.getMessage()).build();

        return handleExceptionInternal(ex, body, new HttpHeaders(), status, request);
    }


    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUso(EntidadeEmUsoException ex, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;

        Problem body = createProblemBuilder(status, ProblemType.ENTIDADE_EM_USO, ex.getMessage()).build();

        return handleExceptionInternal(ex, body, new HttpHeaders(), status, request);
    }


    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {

        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
        } else if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
        }

        String detail = "O corpo da requisicao esta invalido. Verifique erro de sintaxe.";
        Problem body = createProblemBuilder(status, ProblemType.MENSAGEM_IMCOMPREENSIVEL, detail).build();

        return handleExceptionInternal(ex, body, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                        HttpStatus status, WebRequest request) {

        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, headers, status, request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                    HttpHeaders headers,
                                                               HttpStatus status, WebRequest request) {


        String detail = String.format("O parametro de URL '%s' recebeu um valor '%s', que e de um tipo invalido. " +
                "Corrija e informe um valor compativel com o tipo '%s' ",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());
        Problem body = createProblemBuilder(status, ProblemType.PARAMETRO_INVALIDO, detail).build();

        return handleExceptionInternal(ex, body, new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {

        String campo = joinPath(ex.getPath());

        String detail = String.format("A propriedade '%s' não existe." +
                "  Corrija ou remova essa propriedade e tente novamente.", campo);
        Problem body = createProblemBuilder(status, ProblemType.MENSAGEM_IMCOMPREENSIVEL, detail).build();

        return handleExceptionInternal(ex, body, new HttpHeaders(), status, request);

    }


    private String joinPath(List<JsonMappingException.Reference> references) {
        return references.stream()
                .map(ref -> ref.getFieldName())
                .collect(Collectors.joining("."));
    }


    private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex,
                                                                HttpHeaders headers, HttpStatus status,
                                                                WebRequest request) {

        String campo = joinPath(ex.getPath());

        String detail = String.format("A propriedade '%s' recebeu valor '%s', que e de um tipo invalido. " +
                        "Corrija e informe um valor compativo com o tipo %s",
                campo, ex.getValue(), ex.getTargetType().getSimpleName());
        Problem body = createProblemBuilder(status, ProblemType.MENSAGEM_IMCOMPREENSIVEL, detail).build();

        return handleExceptionInternal(ex, body, new HttpHeaders(), status, request);

    }

    private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType,
                                                        String detail) {
        return Problem.builder()
                .status(status.value())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .detail(detail);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {

        if (body == null) {
            body = Problem.builder()
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .build();
        } else if (body instanceof String) {
            body = Problem.builder()
                    .status(status.value())
                    .title((String) body).build();
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

}
