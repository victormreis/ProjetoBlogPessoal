package org.generation.blogpessoal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.generation.blogpessoal.repository.PostagemRepository;

@RestController  // informando que é um controller
@RequestMapping("/postagens") // definindo endpoint
@CrossOrigin("*") // definindo requisições de qualquer forma
public class Postagem {
	
	@Autowired // injetando interface repository
	private PostagemRepository repository;
	
	@GetMapping
	public ResponseEntity<List<org.generation.blogpessoal.model.Postagem>> GetAll(){
		return ResponseEntity.ok(repository.findAll());
	} // criando o metodo de requisital atraves do find all
	
	
	

}
