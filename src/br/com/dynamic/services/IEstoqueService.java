package br.com.dynamic.services;

import java.io.Serializable;

import br.com.dynamic.entidade.Produto;

public interface IEstoqueService extends Serializable {

	public String armazenarProduto(Produto produto, int quantidadeBaixa);

}