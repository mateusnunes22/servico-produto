package br.com.dynamic.repo.interfaces;

import java.io.Serializable;
import java.util.List;

import br.com.dynamic.entidade.Estoque;
import br.com.dynamic.entidade.Produto;

public interface IEstoqueRepo extends IRepo<Estoque, Integer>, Serializable {

	public Estoque findByProduto(Produto produto);

	public List<Estoque> findAllByProduto(Produto produto);

	public List<Estoque> estoqueBaixo();

	public Estoque baixarEstoque(Estoque estoque);

}