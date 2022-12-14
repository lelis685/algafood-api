package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.api.model.input.SenhaInput;
import com.algaworks.algafood.api.model.input.UsuarioComSenhaInput;
import com.algaworks.algafood.api.model.input.UsuarioInput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

@Api(tags = "Usuarios")
public interface UsuarioControllerOpenApi {

	@ApiOperation("Lista os usuarios")
	List<UsuarioModel> listar();

	@ApiOperation("Busca um usuario por ID")
	@ApiResponses({
			@ApiResponse(code = 400, message = "ID do usuario invalido", response = Problem.class),
			@ApiResponse(code = 404, message = "Usuario nao encontrado", response = Problem.class)
	})
	UsuarioModel buscar(
			@ApiParam(value = "ID do usuario", example = "1", required = true)
					Long usuarioId);

	@ApiOperation("Cadastra um usuario")
	@ApiResponses({
			@ApiResponse(code = 201, message = "Usuario cadastrado"),
	})
	UsuarioModel adicionar(
			@ApiParam(name = "corpo", value = "Representacao de um novo usuario", required = true)
					UsuarioComSenhaInput usuarioInput);

	@ApiOperation("Atualiza um usuario por ID")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Usuario atualizado"),
			@ApiResponse(code = 404, message = "Usuario nao encontrado", response = Problem.class)
	})
	UsuarioModel atualizar(
			@ApiParam(value = "ID do usuario", example = "1", required = true)
					Long usuarioId,

			@ApiParam(name = "corpo", value = "Representacao de um usuario com os novos dados",
					required = true)
					UsuarioInput usuarioInput);

	@ApiOperation("Atualiza a senha de um usuario")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Senha alterada com sucesso"),
			@ApiResponse(code = 404, message = "Usuario nao encontrado", response = Problem.class)
	})
	void alterarSenha(
			@ApiParam(value = "ID do usuario", example = "1", required = true)
					Long usuarioId,

			@ApiParam(name = "corpo", value = "Representacao de uma nova senha",
					required = true)
					SenhaInput senha);

}