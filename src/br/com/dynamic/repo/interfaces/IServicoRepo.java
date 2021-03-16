package br.com.dynamic.repo.interfaces;

import java.io.Serializable;
import java.util.List;

import br.com.dynamic.entidade.Categoria;
import br.com.dynamic.entidade.Servico;

public interface IServicoRepo extends IRepo<Servico, Integer>, Serializable {

	public List<Servico> listarPorNome(String nome);

	public Servico buscarNomeLista(String nome);

	public void remover(Servico servico);

	public List<Servico> listarPorCategoria(Categoria categoria);

}