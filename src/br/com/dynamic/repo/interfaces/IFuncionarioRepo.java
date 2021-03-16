package br.com.dynamic.repo.interfaces;

import java.io.Serializable;
import java.util.List;

import br.com.dynamic.entidade.Funcionario;

public interface IFuncionarioRepo extends IRepo<Funcionario, Integer>, Serializable {

	public List<Funcionario> findAllByNome(String nome);

	public Funcionario findByNome(String nome);

}