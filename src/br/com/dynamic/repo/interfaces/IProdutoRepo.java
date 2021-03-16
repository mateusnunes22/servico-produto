package br.com.dynamic.repo.interfaces;

import java.io.Serializable;
import java.util.List;

import br.com.dynamic.entidade.Categoria;
import br.com.dynamic.entidade.Produto;

public interface IProdutoRepo extends IRepo<Produto, Integer>, Serializable {

	public List<Produto> findAllByNome(String nome);

	public Produto findByNome(String nome);
	
	public List<Produto> findAllByCategoria(Categoria categoria);

}