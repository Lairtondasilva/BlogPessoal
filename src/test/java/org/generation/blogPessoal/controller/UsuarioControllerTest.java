package org.generation.blogPessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.generation.blogPessoal.model.Usuario;
import org.generation.blogPessoal.repository.UsuarioRepository;
import org.generation.blogPessoal.services.UsuarioService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioControllerTest {
  
  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private UsuarioService usuarioService;

  @Autowired
  private TestRestTemplate template;

  @BeforeAll
  void start(){
    usuarioRepository.deleteAll();
  }
  @Order(1)
  @Test
  @DisplayName("Cadastrar um usuario")
  public void deveCadastrarUmUsuario(){
    HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(
      new Usuario(0l,"Lairton da Silva", "Lairton@gmail.com","123456789",
      "https://avatars.githubusercontent.com/u/93054001?s=400&u=7ca8a8a7b8c63ef7693d34183b4fcae3f4023afc&v=4",
      "User"
      )
      );
    ResponseEntity<Usuario> resposta = template.exchange(
      "/usuarios/cadastrar", 
      HttpMethod.POST, 
      requisicao, 
      Usuario.class);

      assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
      assertEquals(requisicao.getBody().getNome(), resposta.getBody().getNome());
      assertEquals(requisicao.getBody().getFoto(), resposta.getBody().getFoto());
      assertEquals(requisicao.getBody().getUsuario(), resposta.getBody().getUsuario());
      assertEquals(requisicao.getBody().getFoto(), resposta.getBody().getFoto());
  }

  @Order(2)
  @Test
  @DisplayName("Retorna um usuario pelo id")
  public void deveRetornarPeloId (){
    Usuario usuario = usuarioService.cadastrarUsuario(new Usuario(0L,"Lailson", "kazumi@gmail.com",
"kazumi123", "fdashfadflkadsjfldasj","User")
    );
    
    ResponseEntity<Usuario> response = template.withBasicAuth("root", "root").exchange(
      "/usuarios/"+usuario.getId(),
      HttpMethod.GET,
      null,
      Usuario.class
      );
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(usuario.getNome(), response.getBody().getNome());
    assertEquals(usuario.getFoto(), response.getBody().getFoto());
    assertEquals(usuario.getUsuario(), response.getBody().getUsuario());
  }

  @Order(3)
  @Test
  @DisplayName("Retorna todos os usuarios")
  public void getAll(){
    usuarioService.cadastrarUsuario(new Usuario(0L, 
			"Sabrina Sanches", "sabrina_sanches@email.com.br", "sabrina123", "https://i.imgur.com/5M2p5Wb.jpg","User"));
		
		usuarioService.cadastrarUsuario(new Usuario(0L, 
			"Ricardo Marques", "ricardo_marques@email.com.br", "ricardo123", "https://i.imgur.com/Sk5SjWE.jpg","Admin"));
 
      ResponseEntity<String> response = template.withBasicAuth("root", "root").exchange(
        "/usuarios", 
        HttpMethod.GET,
        null,
        String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Order(4)
  @Test
  @DisplayName("Atulizar um usuário")
  public void deveAtulizarUmUsuario(){
    Usuario user = usuarioService.cadastrarUsuario(new Usuario(0L,
      "Alison",
      "alison@gmail.com",
      "alison123",
      "fkdsajfljdaslfjasdlfsd",
      "User"
      ));

    HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(new Usuario(
      "Alison Vieira",
      "alison@gmail.com",
      "alison123",
      "fkdsajfljdaslfjasdlfsd",
      "User"
     )
    );
    ResponseEntity<Usuario> response = template.withBasicAuth("root",
     "root").exchange("/usuarios/"+user.getId(), 
     HttpMethod.PUT,
     requisicao,
     Usuario.class
     );

     assertEquals(HttpStatus.OK, response.getStatusCode());
     assertEquals(usuarioRepository.findById(user.getId()).get().getNome(),
      response.getBody().getNome());
  }
}
