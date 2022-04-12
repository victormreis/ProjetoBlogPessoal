package org.generation.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.generation.blogpessoal.model.Usuario;
import org.generation.blogpessoal.service.UsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioControllerTest {

	@Autowired
	private UsuarioService service;

	@Autowired
	private TestRestTemplate testeRestTemplate;

	@Test
	@Order(1)
	@DisplayName("Cadastrar apenas um Usuário")
	public void deveCadastrarUmUsuario() {

		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(
				new Usuario(0L, "Victor Reis", "victor@teste.com.br", "123456", "https://google.com.br"));
		ResponseEntity<Usuario> resposta = testeRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST,
				requisicao, Usuario.class);

		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
		assertEquals(requisicao.getBody().getNome(), resposta.getBody().getNome());
		assertEquals(requisicao.getBody().getUsuario(), resposta.getBody().getUsuario());
	}

	@Test
	@Order(2)
	@DisplayName("Não deve permitir duplicação de usuário")
	public void naoDeveDuplicarUsuario() {

		service.CadastrarUsuario(
				new Usuario(0L, "Victor Reis", "victor@teste.com.br", "123456", "https://google.com.br"));

		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(
				new Usuario(0L, "Victor Reis", "victor@teste.com.br", "123456", "https://google.com.br"));

		ResponseEntity<Usuario> resposta = testeRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST,
				requisicao, Usuario.class);

		assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
	}

	@Test
	@Order(3)
	@DisplayName("Alterar um Usuário")
	public void deveAlterarUmUsuario() {

		Optional<Usuario> usuarioCreate = service
				.CadastrarUsuario(new Usuario(0L, "Victor Reis", "victor@teste.com.br", "123456", ""));

		Usuario usuarioUpdate = new Usuario(usuarioCreate.get().getId(), "Victor Martins dos Reis","victor_reis@teste.com.br", "121314", "http://google.com.br");

		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(usuarioUpdate);

		ResponseEntity<Usuario> resposta = testeRestTemplate.withBasicAuth("root", "root")
				.exchange("/usuarios/atualizar", HttpMethod.PUT, requisicao, Usuario.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertEquals(usuarioUpdate.getNome(), resposta.getBody().getNome());
		assertEquals(usuarioUpdate.getUsuario(), resposta.getBody().getUsuario());
		assertEquals(usuarioUpdate.getFoto(), resposta.getBody().getFoto());		
		

	}

}
