package br.com.dynamic.repo.interfaces;

import java.io.Serializable;

import br.com.dynamic.entidade.Usuario;

public interface IUsuarioRepo extends IRepo<Usuario, Integer>, Serializable {
	
	public Usuario findByNome(String nome);

}