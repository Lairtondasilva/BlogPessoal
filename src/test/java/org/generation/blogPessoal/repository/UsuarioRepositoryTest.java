package org.generation.blogPessoal.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.generation.blogPessoal.model.Usuario;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {
  
  @Autowired
  private UsuarioRepository usuarioRepository;

  @BeforeAll
  void start(){
  usuarioRepository.deleteAll();
  usuarioRepository.save(new Usuario(
    0L,
  "Lairton da Silva",
"Lairton@gmail.com", 
"123456789", 
"https://avatars.githubusercontent.com/u/93054001?s=400&u=7ca8a8a7b8c63ef7693d34183b4fcae3f4023afc&v=4"));

 usuarioRepository.save(new Usuario(
  0L,
  "Carlos Silva",
"Carlos@gmail.com", 
"123456789", 
"data:image/jpeg;base64,"));
  }
  @Test
  @DisplayName("deve retornar")
  public void deveRetornarUmUsuario(){
    Optional<Usuario> usuario = usuarioRepository.findByLogin
    ("Carlos@gmail.com");
    assertTrue(usuario.get().getLogin().equals("Carlos@gmail.com"));
  }
  @Test
  @DisplayName("deve retornar 2 usuarios")
  public void deveRetornarDoisUsuarios(){
    List<Usuario> usuarios = usuarioRepository
      .findAllByNomeContainingIgnoreCase("silva");
    assertEquals(2, usuarios.size());
    assertTrue(usuarios.get(0).getNome()
      .equals("Lairton da Silva"));

    assertTrue(usuarios.get(1).getNome()
      .equals("Carlos Silva"));
}
}