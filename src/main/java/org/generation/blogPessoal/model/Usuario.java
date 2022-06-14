package org.generation.blogPessoal.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="tb_usuarios")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(min=2, max=100)
	private String nome;
	
	@NotNull
	@Size(min=5, max=100)
	@Column(unique=true)
	private String usuario;
	
	@NotNull
	@Size(min=5, max=100)
	private String senha;

	
	@NotNull
	@Size(min = 3, max = 255)
	private String foto;

	private String tipo;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties("usuario")
	private List<Postagem> postagens;
	


	public Usuario() {
	}

	public Usuario(Long id,String nome,
		String usuario, String senha, String foto, String tipo) {
		this.id = id;
		this.nome = nome;
		this.usuario = usuario;
		this.senha = senha;
		this.foto = foto;
		this.tipo = tipo;
	}
	

	public Usuario(@NotNull @Size(min = 2, max = 100) String nome, @NotNull @Size(min = 5, max = 100) String usuario,
			@NotNull @Size(min = 5, max = 100) String senha, @NotNull @Size(min = 3, max = 255) String foto, String tipo) {
		this.nome = nome;
		this.usuario = usuario;
		this.senha = senha;
		this.foto = foto;
		this.tipo = tipo;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List<Postagem> getPostagem() {
		return postagens;
	}


	public void setPostagem(List<Postagem> postagens) {
		this.postagens= postagens;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}
	

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	
	
}
