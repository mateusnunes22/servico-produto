package br.com.dynamic.repo.interfaces;

import java.io.Serializable;
import java.util.List;

import br.com.dynamic.entidade.Categoria;

public interface ICategoriaRepo extends IRepo<Categoria, Integer>, Serializable {

	public Categoria findByDescricao(String descricao);

	public List<Categoria> findAllByDescricao(String categoria);

}