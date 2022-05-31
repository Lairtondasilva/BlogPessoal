package org.generation.blogPessoal.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.generation.blogPessoal.model.Tema;
import org.generation.blogPessoal.repository.TemaRepository;
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
@CrossOrigin(origins="*",allowedHeaders="*")
@RequestMapping("/temas")
public class TemaController {
	
	@Autowired
	private TemaRepository repository;
	
	@GetMapping
	public ResponseEntity<List<Tema>> getAll(){
		List<Tema> temas = repository.findAll();
		if(temas.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		else {
			for(Tema tema:temas) {
				long id = tema.getId();
				tema.add(linkTo(methodOn(TemaController.class).getById(id)).withSelfRel().withType("GET"));
			}
			return ResponseEntity.ok(temas);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Tema> getById(@PathVariable Long id) {
		return repository.findById(id).map(tema->{
			tema.add(linkTo(methodOn(TemaController.class).getAll()).withRel("All").withType("GET"));
			return ResponseEntity.ok(tema);
		}).orElse(ResponseEntity.notFound().build());
		
		/*Optional<Tema> tema = repository.findById(id);
		if(!tema.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}else {
			tema.get().add(linkTo(methodOn(TemaController.class).getAll()).withRel("All").withType("GET"));
			return ResponseEntity.ok(tema.get());
		}*/
	}
	
	@GetMapping("/nome/{descricao}")
	public ResponseEntity<List<Tema>> getByDescricao(@PathVariable String descricao){
		List<Tema> temas = repository.findAllByDescricaoContainingIgnoreCase(descricao);
		if(temas.isEmpty()) {
			return ResponseEntity.notFound().build();
		}else {
			for (Tema tema:temas) {
				Long id = tema.getId();
				tema.add(linkTo(methodOn(TemaController.class).getAll()).withRel("All").withType("GET"));
				tema.add(linkTo(methodOn(TemaController.class).getById(id)).withSelfRel());
			}
			
			return ResponseEntity.ok(temas);
		}
	}
	
	@PostMapping
	public ResponseEntity<Tema> post(@RequestBody Tema tema){
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(tema));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Tema> put (@RequestBody Tema tema, @PathVariable Long id){
		return repository.findById(id).map(temaAntigo->{
			temaAntigo.setDescricao(tema.getDescricao());
			return ResponseEntity.ok().body(repository.save(temaAntigo));
		}).orElse(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		repository.deleteById(id);
	}
	
	
}
