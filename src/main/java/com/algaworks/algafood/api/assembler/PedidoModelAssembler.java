package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.*;
import com.algaworks.algafood.api.model.*;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoModelAssembler() {
        super(PedidoController.class, PedidoModel.class);
    }

    @Override
    public PedidoModel toModel(Pedido pedido) {
        PedidoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoModel);

        FormaPagamentoModel formaPagamentoModel = pedidoModel.getFormaPagamento();
        UsuarioModel usuarioModel = pedidoModel.getCliente();
        RestauranteResumoModel modelRestaurante = pedidoModel.getRestaurante();
        CidadeResumoModel cidadeResumoModel = pedidoModel.getEnderecoEntrega().getCidade();

        pedidoModel.add(linkTo(PedidoController.class).withRel("pedidos"));

        formaPagamentoModel.add(linkTo(methodOn(FormaPagamentoController.class)
                .buscar(formaPagamentoModel.getId(), null)).withSelfRel());

        usuarioModel.add(linkTo(methodOn(UsuarioController.class)
                .buscar(usuarioModel.getId())).withSelfRel());

        modelRestaurante.add(linkTo(methodOn(RestauranteController.class)
                .buscar(modelRestaurante.getId())).withSelfRel());

        cidadeResumoModel.add(linkTo(methodOn(CidadeController.class)
                .buscar(cidadeResumoModel.getId())).withSelfRel());

        pedidoModel.getItens().forEach(item -> {
            item.add(linkTo(methodOn(RestauranteProdutoController.class)
                    .buscar(pedidoModel.getRestaurante().getId(), item.getProdutoId())).withRel("produto"));
        });

        return pedidoModel;
    }

    @Override
    public CollectionModel<PedidoModel> toCollectionModel(Iterable<? extends Pedido> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(PedidoController.class).withSelfRel());
    }
}