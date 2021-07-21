package org.generattion.blogPessoal.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.generattion.blogPessoal.model.UserLongin;
import org.generattion.blogPessoal.model.Usuario;
import org.generattion.blogPessoal.repository.UsuarioRepository;
import org.generattion.blogPessoal.service.UsuarioService;
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

@RestController
@RequestMapping ("/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	public @PostMapping ("/logar") ResponseEntity<UserLongin> Autentication(@RequestBody Optional<UserLongin> user){
		return usuarioService.Logar(user)
				.map(userAutorizado -> ResponseEntity.ok(userAutorizado))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
	
	public @PostMapping ("/cadastrar") ResponseEntity<Optional<Usuario>> post(@Valid @RequestBody Usuario usuario){
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.cadastrarUsuario(usuario));
	}
	
	@GetMapping
	ResponseEntity<List<Usuario>> getAllUsuario() {
		List<Usuario> listaDeUsuarios = usuarioRepository.findAll();

		if (!listaDeUsuarios.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(listaDeUsuarios);
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}

	@GetMapping("id/{id}")
	ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
		return usuarioRepository.findById(id).map(resp -> ResponseEntity.ok().body(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PutMapping
	public ResponseEntity<Usuario> put (@RequestBody Usuario usuario){
		return ResponseEntity.ok(usuarioRepository.save(usuario));
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id) {
		usuarioRepository.deleteById(id);
	}
	
}
