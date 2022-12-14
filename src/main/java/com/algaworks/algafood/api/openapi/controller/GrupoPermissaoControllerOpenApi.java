package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.PermissaoModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

@Api(tags = "Grupos")
public interface GrupoPermissaoControllerOpenApi {

	@ApiOperation("Lista as permissoes associadas a um grupo")
	@ApiResponses({
			@ApiResponse(code = 400, message = "ID do grupo invalido", response = Problem.class),
			@ApiResponse(code = 404, message = "Grupo nao encontrado", response = Problem.class)
	})
	List<PermissaoModel> listar(
			@ApiParam(value = "ID do grupo", example = "1", required = true)
					Long grupoId);

	@ApiOperation("Desassociacao de permissao com grupo")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Desassociacao realizada com sucesso"),
			@ApiResponse(code = 404, message = "Grupo ou permissao nao encontrada",
					response = Problem.class)
	})
	void desassociar(
			@ApiParam(value = "ID do grupo", example = "1", required = true)
					Long grupoId,

			@ApiParam(value = "ID da permissao", example = "1", required = true)
					Long permissaoId);

	@ApiOperation("Associacao de permissao com grupo")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Associacao realizada com sucesso"),
			@ApiResponse(code = 404, message = "Grupo ou permissao nao encontrada",
					response = Problem.class)
	})
	void associar(
			@ApiParam(value = "ID do grupo", example = "1", required = true)
					Long grupoId,

			@ApiParam(value = "ID da permissao", example = "1", required = true)
					Long permissaoId);

}