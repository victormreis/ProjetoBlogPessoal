package org.generation.blogpessoal.service;


import java.nio.charset.Charset;
import java.util.Optional;
import org.apache.commons.codec.binary.Base64;

import org.generation.blogpessoal.model.Usuario;
import org.generation.blogpessoal.model.UsuarioLogin;
import org.generation.blogpessoal.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;

	public Optional<Usuario> CadastrarUsuario(Usuario usuario) {

		if (repository.findByUsuario(usuario.getUsuario()).isPresent()) {
			return Optional.empty();
		}

		usuario.setSenha(criptografarSenha(usuario.getSenha()));

		return Optional.of(repository.save(usuario));
	}

	private boolean compararSenhas (String senhaDigitada, String senhaDoBanco) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		return encoder.matches(senhaDigitada, senhaDoBanco);
	}

	private String criptografarSenha(String senha) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		return encoder.encode(senha);
	}

	private String geraradorBasicToken(String usuario, String senha) {

		String token = usuario + ":" + senha;
		byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));
		
		return "Basic " + new String (tokenBase64);

	}

	public Optional<UsuarioLogin> Logar(Optional<UsuarioLogin> user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<Usuario> usuario = repository.findByUsuario(user.get().getUsuario());

		if (usuario.isPresent()) {
			if (compararSenhas(user.get().getSenha(), usuario.get().getSenha())) {
				user.get().setId(user.get().getId());
				user.get().setNome(user.get().getNome());
				user.get().setFoto(user.get().getFoto());
				user.get().setSenha(user.get().getSenha());
				user.get().setToken(geraradorBasicToken(user.get().getUsuario(), user.get().getSenha()));

				return user;
			}
		}

		return Optional.empty();
	}
}
