package br.com.dynamic.repo.interfaces;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import br.com.dynamic.entidade.Cliente;
import br.com.dynamic.entidade.Funcionario;
import br.com.dynamic.entidade.Produto;
import br.com.dynamic.entidade.Registro;
import br.com.dynamic.entidade.Servico;

public interface IRegistroRepo extends IRepo<Registro, Integer>, Serializable {

	public List<Registro> findByCliente(Cliente cliente);

	public Registro BuscarVenda(Registro venda);

	public Registro findByVenda(Registro registro);
	
	public List<Registro> findAll(Date dataInicial, Date dataFinal);

	public List<Registro> findAllByFuncionario(Funcionario funcionario);
	
	public List<Registro> findAllByProduto(Produto produto, Date dataInicial, Date dataFinal);

	public List<Registro> findAllByServico(Servico servico, Date dataInicial, Date dataFinal);
	
	public List<Registro> findAllServicos(Date dataInicial, Date dataFinal);

	public List<Registro> BuscarAgendamento(Date data);

	public Registro agendar(Registro registro);

	public Registro comprar(Registro registro);

	public Registro despesa(Registro registro);

	public Registro vender(Registro registro);

	public Registro venderRetroativo(Registro registro, int idCliente, Date data);

	public void removerVendaServico(Registro registro);

	public List<Registro> findAllByVenda(Integer id);

	

}