package com.algaworks.algafood;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

@SpringBootTest
class CadastroCozinhaIT {

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@Test
	public void deveAtribuirId_QuandoCadastrarCozinhaComDadosCorretos(){
		Cozinha cozinha = new Cozinha();
		cozinha.setNome("chinesa");

		cozinha = cadastroCozinha.salvar(cozinha);

		assert cozinha != null;
		assert cozinha.getId() != null;
	}

	@Test
	public void deveFalhar_QuandoCadastrarCozinhaSemNome(){
		Cozinha cozinha = new Cozinha();

		ConstraintViolationException erroEsperado =
				Assertions.assertThrows(ConstraintViolationException.class, ()->{
					cadastroCozinha.salvar(cozinha);
				});

		assert erroEsperado != null;
	}

	@Test
	public void deveFalhar_QuandoExcluirCozinhaInexistente(){

		CozinhaNaoEncontradaException erroEsperado =
				Assertions.assertThrows(CozinhaNaoEncontradaException.class, ()->{
					cadastroCozinha.excluir(10L);
				});

		assert erroEsperado != null;
	}


	@Test
	public void deveFalhar_QuandoExcluirCozinhaEmUso(){

		EntidadeEmUsoException erroEsperado =
				Assertions.assertThrows(EntidadeEmUsoException.class, ()->{
					cadastroCozinha.excluir(1L);
				});

		assert erroEsperado != null;
	}



}
