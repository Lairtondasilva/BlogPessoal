package org.generation.blogPessoal.services;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.generation.blogPessoal.model.Usuario;
import org.generation.blogPessoal.model.UsuarioLogin;
import org.generation.blogPessoal.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario cadastrarUsuario(Usuario usuario) {
		if(usuarioRepository.findByLogin(usuario.getLogin()).isPresent())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O usuario Já existe", null);

		usuario.setSenha(criptografarSenha(usuario.getSenha()));
		return usuarioRepository.save(usuario);
	}
	
	public Optional<UsuarioLogin> Logar (Optional<UsuarioLogin> user){
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<Usuario> usuario = usuarioRepository.findByLogin(user.get().getlogin());
		
		if(usuario.isPresent()) {
			if(encoder.matches(user.get().getSenha(), usuario.get().getSenha())) {
				String auth = user.get().getlogin() + ":" + user.get().getSenha();
				
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String AuthHeader = "Basic " + new String(encodedAuth);
				
				user.get().setToken(AuthHeader);
				user.get().setNome(usuario.get().getNome());
				
				return user;
			}
		}
		return null;
	}
	public Optional<Usuario> atualizarUsuario(Usuario usuario, Long id) {
		
		if(usuarioRepository.findById(id).isPresent()) {
			
		
			Optional<Usuario> buscaUsuario = usuarioRepository.findByLogin(usuario.getLogin());
			
		
			if ( (buscaUsuario.isPresent()) && ( buscaUsuario.get().getId() != id))
				throw new ResponseStatusException(
						HttpStatus.BAD_REQUEST, "Usuário já existe!", null);

			usuario.setSenha(criptografarSenha(usuario.getSenha()));
			usuario.setId(id);

			return Optional.ofNullable(usuarioRepository.save(usuario));
			
		}
		return Optional.empty();
	}	

	private String criptografarSenha(String senha) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		return encoder.encode(senha);

	}

}
