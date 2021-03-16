package br.com.dynamic.repo.interfaces;

import java.io.Serializable;
import java.util.List;

import br.com.dynamic.entidade.Cliente;
import br.com.dynamic.entidade.Funcionario;

public interface IClienteRepo extends IRepo<Cliente, Integer>, Serializable {

	public Cliente findByNome(String nome);

	public List<Cliente> findAllByNome(String nome);
	
	public List<Cliente> listarAniversariantes(int numeroDias);

	public List<Cliente> listarTodosIndicadosPorFuncionario(Funcionario funcionario);
	
	public List<Cliente> listarTodosIndicadosPorCliente(Cliente cliente);

}