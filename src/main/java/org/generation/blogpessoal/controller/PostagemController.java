package org.generation.blogpessoal.controller;

import java.util.List;

import org.generation.blogpessoal.model.Postagem;
import org.generation.blogpessoal.repository.PostagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // informando que é um controller
@RequestMapping("/postagens") // definindo endpoint
@CrossOrigin(origins = "*") // definindo requisições de qualquer forma
public class PostagemController {

	@Autowired // transferencia de responsabilidade para o repository
	private PostagemRepository repository; // injetando interface repository

	@GetMapping
	public ResponseEntity<List<Postagem>> getAll() {
		return ResponseEntity.ok(repository.findAll());
	} // criando o metodo de requisição atraves do find all

	@GetMapping("/{id}") // requisição do tipo get em postagem com valor Id
	public ResponseEntity<Postagem> getById(@PathVariable Long id) // metodo captura valor pelo PathVariable
	{
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/titulo/{titulo}") // Requisição via Titulo da postagem
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo) // captura titulo ou parte do titulo pelo PathVariable														 
	{
		return ResponseEntity.ok(repository.findAllByTituloContainingIgnoreCase(titulo));
	}																									 
					
	@PostMapping // Post para inserção na base de dados
	public ResponseEntity<Postagem> post(@RequestBody Postagem postagem) // requestbody para solicitar os dados via body na requisiçao
	{																		
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(postagem)); // metodo http retorna 201  created e o save salva na base de dados
																							
	}

	@PutMapping
	public ResponseEntity<Postagem> put(@RequestBody Postagem postagem) // atualização na base de dados  metodo http retorna 200 ok e o save salva na base de dados
	{ 
		return ResponseEntity.status(HttpStatus.OK).body(repository.save(postagem)); 
																						
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		repository.deleteById(id);
	}

}
