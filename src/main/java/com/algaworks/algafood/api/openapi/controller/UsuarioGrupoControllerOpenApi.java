package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.GrupoModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

@Api(tags = "Usuarios")
public interface UsuarioGrupoControllerOpenApi {

	@ApiOperation("Lista os grupos associados a um usuario")
	@ApiResponses({
			@ApiResponse(code = 404, message = "Usuario nao encontrado", response = Problem.class)
	})
	List<GrupoModel> listar(
			@ApiParam(value = "ID do usuario", example = "1", required = true)
					Long usuarioId);

	@ApiOperation("Desassociacao de grupo com usuario")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Desassociacao realizada com sucesso"),
			@ApiResponse(code = 404, message = "Usuario ou grupo nao encontrado",
					response = Problem.class)
	})
	void desassociar(
			@ApiParam(value = "ID do usuario", example = "1", required = true)
					Long usuarioId,

			@ApiParam(value = "ID do grupo", example = "1", required = true)
					Long grupoId);

	@ApiOperation("Associacao de grupo com usuario")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Associacao realizada com sucesso"),
			@ApiResponse(code = 404, message = "Usuario ou grupo nao encontrado",
					response = Problem.class)
	})
	void associar(
			@ApiParam(value = "ID do usuario", example = "1", required = true)
					Long usuarioId,

			@ApiParam(value = "ID do grupo", example = "1", required = true)
					Long grupoId);

}