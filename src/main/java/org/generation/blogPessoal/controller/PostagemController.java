package org.generation.blogPessoal.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.generation.blogPessoal.model.Postagem;
import org.generation.blogPessoal.repository.Postagemrepository;
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
@RequestMapping("/postagens")
@CrossOrigin(origins="*", allowedHeaders="*")
public class PostagemController {

	@Autowired
	private Postagemrepository postagemRepository;
	
	@GetMapping
	public ResponseEntity<List<Postagem>> getAll(){
		List<Postagem> postagens = postagemRepository.findAll();
		
		if(postagens.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}else {
			for(Postagem post:postagens) {
				Long id = post.getId();
				post.add(linkTo(methodOn(PostagemController.class).getById(id)).withSelfRel());
			}
			return ResponseEntity.ok(postagens);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Postagem> getById(@PathVariable Long id){
		return postagemRepository.findById(id).map(post->{
			post.add(linkTo(methodOn(PostagemController.class).getAll()).withRel("Lista postagens").withType("GET"));//adiciona link do getAll()
			return ResponseEntity.ok(post);
		}).orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo){
		List<Postagem> postagens = postagemRepository.findByTituloContainingIgnoreCase(titulo);
		if(postagens.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}else {
			for(Postagem post:postagens) {
				Long id = post.getId();
				post.add(linkTo(methodOn(PostagemController.class).getAll()).withRel("todas as postagens"));
				post.add(linkTo(methodOn(PostagemController.class).getById(id)).withSelfRel());
			}
			return ResponseEntity.ok(postagens);
		}
	}
	
	@PostMapping
	public ResponseEntity<Postagem> post (@RequestBody Postagem postagem){
		return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Postagem> put (@RequestBody Postagem postagem, @PathVariable Long id ){
		return postagemRepository.findById(id).map(post->{
		post.setTitulo(postagem.getTitulo());
		post.setTexto(postagem.getTexto());
		post.setData(postagem.getData());
		return ResponseEntity.status(HttpStatus.OK).body(postagemRepository.save(post));
		}).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		postagemRepository.deleteById(id);
	}
}
