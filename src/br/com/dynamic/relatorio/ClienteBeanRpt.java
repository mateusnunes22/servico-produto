package br.com.dynamic.relatorio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.persistence.NoResultException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import br.com.dynamic.entidade.Cliente;
import br.com.dynamic.entidade.Funcionario;
import br.com.dynamic.entidade.Registro;
import br.com.dynamic.estrutura.Bean;
import br.com.dynamic.estrutura.ClienteRelatorio;
import br.com.dynamic.infra.factories.ComparatorUtil;
import br.com.dynamic.repo.interfaces.IClienteRepo;
import br.com.dynamic.repo.interfaces.IFuncionarioRepo;
import br.com.dynamic.repo.interfaces.IRelatorioRepo;
import br.com.dynamic.services.IRelatorioService;

@ManagedBean
@SessionScoped
public class ClienteBeanRpt implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value="#{relatorioService}")
	private IRelatorioService relatorioService;
	
	@ManagedProperty(value="#{relatorioRepo}")
	private IRelatorioRepo relatorioRepo;
	
	@ManagedProperty(value="#{clienteRepo}")
	private IClienteRepo clienteRepo;
	
	@ManagedProperty(value="#{funcionarioRepo}")
	private IFuncionarioRepo funcionarioRepo;
	
	private Cliente cliente, clienteIndicacao;
	private Funcionario funcionarioIndicacao;
	private Boolean somenteIndicado;

	private List<Cliente> clientes;
	private List<Cliente> clientesIndicacao;
	private List<Funcionario> funcionarios;
	
	private Bean rpt = new Bean();
	public List<ClienteRelatorio> relatorio =  new ArrayList<ClienteRelatorio>();
	public List<Registro> relatorioDetalhado =  new ArrayList<Registro>();
	
	public void init() {
		if (somenteIndicado != null && somenteIndicado) {
			clientes.clear();
		} else {
			clientes = clienteRepo.getAll();
			Collections.sort(clientes, ComparatorUtil.getClienteComparator());
		}

		clientesIndicacao = clienteRepo.getAll();
		funcionarios = funcionarioRepo.getAll();
		Collections.sort(clientesIndicacao, ComparatorUtil.getClienteComparator());
		Collections.sort(funcionarios, ComparatorUtil.getFuncionarioComparator());
	}
	
	public String atualizar() {
		List<Cliente> clientes = new ArrayList<Cliente>();
		
		if (somenteIndicado && funcionarioIndicacao != null) {
			clientes = clienteRepo.listarTodosIndicadosPorFuncionario(funcionarioIndicacao);
		} else if (somenteIndicado && clienteIndicacao != null) {
			clientes = clienteRepo.listarTodosIndicadosPorCliente(clienteIndicacao);
		} else if (cliente != null) {
			clientes.add(cliente);
		} else {
			clientes.addAll(this.clientes);
		}
		
		relatorio = relatorioService.totalizarComprasClientes(clientes, rpt.getData());

		return null;
	}
	
	public String detalhar(ClienteRelatorio cliente) {
		try {
			rpt.setTexto(cliente.getNome());
			relatorioDetalhado = relatorioRepo.relatorioClienteDetalhado(rpt);
		} catch(NoResultException e) {
			Logger.getLogger(getClass().getName()).log(Level.WARN, e);
		}
		return "detalhar";
	}
	
	public void somenteIndicadoChanged() {
		if (somenteIndicado) {
			clientes.clear();
		} else {
			clientes = clienteRepo.getAll();
			Collections.sort(clientes, ComparatorUtil.getClienteComparator());
		}
	}
	
	public void funcionarioChanged() {
		clienteIndicacao = null;
	}
	
	public void clienteChanged() {
		funcionarioIndicacao = null;
	}
	
	public List<ClienteRelatorio> getRelatorio() {
		return relatorio;
	}

	public List<Registro> getRelatorioDetalhado() {
		return relatorioDetalhado;
	}

	public void setRelatorioDetalhado(List<Registro> relatorioDetalhado) {
		this.relatorioDetalhado = relatorioDetalhado;
	}

	public void setRelatorio(List<ClienteRelatorio> relatorio) {
		this.relatorio = relatorio;
	}

	public void setRpt(Bean rpt) {
		this.rpt = rpt;
	}

	public Bean getRpt() {
		return rpt;
	}

	public Boolean getSomenteIndicado() {
		return somenteIndicado;
	}

	public void setSomenteIndicado(Boolean somenteIndicado) {
		this.somenteIndicado = somenteIndicado;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Cliente getClienteIndicacao() {
		return clienteIndicacao;
	}

	public void setClienteIndicacao(Cliente clienteIndicacao) {
		this.clienteIndicacao = clienteIndicacao;
	}

	public Funcionario getFuncionarioIndicacao() {
		return funcionarioIndicacao;
	}

	public void setFuncionarioIndicacao(Funcionario funcionarioIndicacao) {
		this.funcionarioIndicacao = funcionarioIndicacao;
	}
	
	public List<Cliente> getClientes() {
		return clientes;
	}

	public List<Cliente> getClientesIndicacao() {
		return clientesIndicacao;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public IRelatorioService getRelatorioService() {
		return relatorioService;
	}

	public void setRelatorioService(IRelatorioService relatorioService) {
		this.relatorioService = relatorioService;
	}

	public IRelatorioRepo getRelatorioRepo() {
		return relatorioRepo;
	}

	public void setRelatorioRepo(IRelatorioRepo relatorioRepo) {
		this.relatorioRepo = relatorioRepo;
	}

	public IClienteRepo getClienteRepo() {
		return clienteRepo;
	}

	public void setClienteRepo(IClienteRepo clienteRepo) {
		this.clienteRepo = clienteRepo;
	}

	public IFuncionarioRepo getFuncionarioRepo() {
		return funcionarioRepo;
	}

	public void setFuncionarioRepo(IFuncionarioRepo funcionarioRepo) {
		this.funcionarioRepo = funcionarioRepo;
	}

}