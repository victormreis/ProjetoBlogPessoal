package org.generation.blogpessoal.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.generation.blogpessoal.model.Usuario;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@BeforeAll
	void start() {

		usuarioRepository
				.save(new Usuario(0L, "Victor Reis", "victor@teste.com.br", "123456", "https://google.com.br"));
		usuarioRepository
				.save(new Usuario(0L, "Daiane Silva reis", "daiane@teste.com.br", "123456", "https://google.com.br"));
		usuarioRepository
				.save(new Usuario(0L, "Ednaldo pereira ", "ednaldo@teste.com.br", "123456", "https://google.com.br"));
		usuarioRepository
				.save(new Usuario(0L, "Valdecir Reis", "valdecir@teste.com.br", "123456", "https://google.com.br"));
	}

	@Test
	@DisplayName("Retorna apenas um usuario")
	public void deveRetornarUmUsuario() {

		Optional<Usuario> usuario = usuarioRepository.findByUsuario("victor@teste.com.br");
		assertTrue(usuario.get().getUsuario().equals("victor@teste.com.br"));
	}

	@Test
	@DisplayName("Retorna Dois usuarios")
	public void deveRetornarDoisUsuarios() {
		
		List<Usuario> listaDeusuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Reis");
		assertEquals(2, listaDeusuarios.size());

		assertTrue(listaDeusuarios.get(0).getNome().equals("Victor Reis"));
		assertTrue(listaDeusuarios.get(1).getNome().equals("Valdecir Reis"));

	}

}
