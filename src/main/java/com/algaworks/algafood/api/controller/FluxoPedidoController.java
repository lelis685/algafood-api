package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.openapi.controller.FluxoPedidoControllerOpenApi;
import com.algaworks.algafood.domain.service.FluxoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/pedidos/{codigoPedido}")
public class FluxoPedidoController implements FluxoPedidoControllerOpenApi {

	@Autowired
	private FluxoPedidoService fluxoPedido;
	
	@Override
	@PutMapping("/confirmacao")
	public ResponseEntity<Void> confirmar(@PathVariable String codigoPedido) {
		fluxoPedido.confirmar(codigoPedido);

		return ResponseEntity.noContent().build();
	}

	@Override
	@PutMapping("/cancelamento")
	public ResponseEntity<Void> cancelar(@PathVariable String codigoPedido) {
		fluxoPedido.cancelar(codigoPedido);
		return ResponseEntity.noContent().build();
	}

	@Override
	@PutMapping("/entrega")
	public ResponseEntity<Void> entregar(@PathVariable String codigoPedido) {
		fluxoPedido.entregar(codigoPedido);
		return ResponseEntity.noContent().build();
	}
	
}
