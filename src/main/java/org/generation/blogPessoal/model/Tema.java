package org.generation.blogPessoal.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.hateoas.RepresentationModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@Entity
@Table(name = "tb_temas")
public class Tema extends RepresentationModel<Tema> implements Serializable{
	    
	    @Id	
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		
		@NotBlank(message = "O atributo Descrição é obrigatório e não pode conter espaços em branco")
		private String descricao;
		
		@OneToMany(mappedBy = "tema", cascade = CascadeType.REMOVE)
		@JsonIgnoreProperties("tema")
		private List<Postagem> postagens;
		
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getDescricao() {
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}

		public List<Postagem> getPostagens() {
			return postagens;
		}

		public void setPostagens(List<Postagem> postagens) {
			this.postagens = postagens;
		}
		
}