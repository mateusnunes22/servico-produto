package br.com.dynamic.repo.interfaces;

import java.io.Serializable;
import java.util.List;

import br.com.dynamic.entidade.Fornecedor;

public interface IFornecedorRepo extends IRepo<Fornecedor, Integer>, Serializable {

	public List<Fornecedor> findAllByNome(String nome);

	public Fornecedor findByNome(String nome);

}