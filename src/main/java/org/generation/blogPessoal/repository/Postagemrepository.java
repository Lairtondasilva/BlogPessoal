package org.generation.blogPessoal.repository;

import java.util.List;

import org.generation.blogPessoal.model.Postagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface Postagemrepository extends JpaRepository<Postagem, Long>{
	List<Postagem> findByTitulo(String titulo);
}