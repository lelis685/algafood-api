package com.algaworks.algafood.api.openapi.controller;

import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import org.springframework.format.annotation.DateTimeFormat;

@Api(tags = "Pedidos")
public interface PedidoControllerOpenApi {

	@ApiOperation("Pesquisa os pedidos")

	@ApiImplicitParams({
			@ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
					name = "campos", paramType = "query", type = "string"),
			@ApiImplicitParam(name = "clienteId", value = "ID do cliente para filtro da pesquisa",
					example = "1", dataType = "int"),

			@ApiImplicitParam(name = "restauranteId", value = "ID do restaurante para filtro da pesquisa",
					example = "1", dataType = "int"),

			@ApiImplicitParam(name = "dataCriacaoInicio", value = "Data/hora de criação inicial para filtro da pesquisa",
					example = "2019-12-01T00:00:00Z", dataType = "date-time"),

			@ApiImplicitParam(name = "dataCriacaoFim", value = "Data/hora de criação final para filtro da pesquisa",
					example = "2019-12-02T23:59:59Z", dataType = "date-time")
	})
	Page<PedidoResumoModel> pesquisar(PedidoFilter filtro, Pageable pageable);

	@ApiOperation("Registra um pedido")
	@ApiResponses({
			@ApiResponse(code = 201, message = "Pedido registrado"),
	})
	PedidoModel adicionar(
			@ApiParam(name = "corpo", value = "Representação de um novo pedido", required = true)
			PedidoInput pedidoInput);

	@ApiOperation("Busca um pedido por código")
	@ApiResponses({
			@ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
	})
	@ApiImplicitParams({
			@ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por virgula",
					name = "campos", paramType = "query", type = "string")
	})
	PedidoModel buscar(
			@ApiParam(value = "Código de um pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55",
					required = true)
			String codigoPedido);

}
