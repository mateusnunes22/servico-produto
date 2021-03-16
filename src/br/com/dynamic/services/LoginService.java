package br.com.dynamic.services;

import javassist.NotFoundException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dynamic.entidade.Usuario;
import br.com.dynamic.infra.excecoes.SenhaInvalidaException;
import br.com.dynamic.repo.interfaces.IUsuarioRepo;

@Service
public class LoginService implements ILoginService {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IUsuarioRepo usuarioRepo;
	
	@Override
	public Usuario authorize(String username, String password) throws NotFoundException {
		Usuario user = usuarioRepo.findByNome(username);
		
		if (user != null && user.getSenha().equals(password)) {
			return user;
		} else if (user != null) {
			throw new SenhaInvalidaException();
		}

		throw new NotFoundException("usuario.erro.invalido");
	}
	
}
