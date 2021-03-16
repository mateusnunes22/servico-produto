package br.com.dynamic.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import br.com.dynamic.entidade.Cliente;
import br.com.dynamic.entidade.Funcionario;
import br.com.dynamic.estrutura.Bean;
import br.com.dynamic.infra.factories.ComparatorUtil;
import br.com.dynamic.repo.interfaces.IClienteRepo;
import br.com.dynamic.repo.interfaces.IFuncionarioRepo;
import br.com.dynamic.util.SessionUtil;

@ManagedBean
@SessionScoped
public class ClienteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value="#{clienteRepo}")
	private IClienteRepo clienteRepo;
	
	@ManagedProperty(value="#{funcionarioRepo}")
	private IFuncionarioRepo funcionarioRepo;
	
	@ManagedProperty("#{msg}")
	private ResourceBundle bundle;
	
	private Cliente cliente = new Cliente();
	private Bean clienteList = new Bean(), clienteHome = new Bean();
	public List<Cliente> lista = new ArrayList<Cliente>();
	
	public List<Funcionario> funcionarios;
	public List<Cliente> clientes;
	public List<Cliente> clientesHome;
	
	public void init() {
		funcionarios = funcionarioRepo.getAll();
		clientes = clienteRepo.getAll();
		
		Collections.sort(funcionarios, ComparatorUtil.getFuncionarioComparator());
		Collections.sort(clientes, ComparatorUtil.getClienteComparator());
	}
	
	public String atualizar()  {
		String nome = (clienteList.getTexto() == null) ? "" : clienteList.getTexto(); 
		lista = clienteRepo.findAllByNome(nome);
		Collections.sort(lista, ComparatorUtil.getClienteComparator());
		clienteList.setSize(lista.size());

		return null;
	}
	
	public String atualizarHome()  {
		String nome = (clienteHome.getTexto() == null) ? "" : clienteHome.getTexto(); 
		clientesHome = clienteRepo.findAllByNome(nome);
		Collections.sort(clientesHome, ComparatorUtil.getClienteComparator());
		clienteHome.setSize(clientesHome.size());

		return null;
	}

	public void verificarNome() {
		Cliente clienteNome = clienteRepo.findByNome(cliente.getNome());
		
		if (clienteNome != null) {
			Logger.getLogger(getClass().getName()).log(Level.WARN, bundle.getString("cliente.erro.nome.duplicado"));
			FacesContext.getCurrentInstance().addMessage("consulta:id",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("cliente.erro.nome.duplicado"), ""));
		}
	}

	public String salvar() {
		String retorno = null;
		
		if (cliente.getFuncionario() == null && cliente.getCliente() == null) {
			SessionUtil.addErrorMessage("cliente.required.indicacao");
			return null;
		}
		
		try {
			if (cliente.getId() == null) {
				clienteRepo.add(cliente);
			} else {
				clienteRepo.update(cliente);
				retorno = "edit";
			}
			cliente = new Cliente();
			SessionUtil.addSuccessMessage("OperacaoSucesso");
		} catch (PersistenceException e) {
			Logger.getLogger(getClass().getName()).log(Level.WARN,  bundle.getString("default.erro.validacao"), e);
			SessionUtil.addErrorMessage("OperacaoFracasso");
		}

		return retorno;
	}
	
	public String novo() {
		cliente = new Cliente();
		return "edit";
	}

	public String editar(Cliente cliente) {
		this.cliente = cliente;
		return "edit";
	}

	public String agendar(Cliente cliente) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("clienteAgendamento", cliente);
		return "agendar";
	}

	public String vender(Cliente cliente) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("clienteVenda", cliente);
		return "vender";
	}
	
	public void funcionarioChanged() {
		cliente.setCliente(null);
	}
	
	public void clienteChanged() {
		cliente.setFuncionario(cliente.getCliente().getFuncionario());
	}

	public List<Cliente> getLista() {
		return lista;
	}

	public void setLista(List<Cliente> lista) {
		this.lista = lista;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Bean getClienteList() {
		return clienteList;
	}

	public void setClienteList(Bean clienteList) {
		this.clienteList = clienteList;
	}

	public Bean getClienteHome() {
		return clienteHome;
	}

	public void setClienteHome(Bean clienteHome) {
		this.clienteHome = clienteHome;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}
	
	public List<Cliente> getClientes() {
		return clientes;
	}

	public List<Cliente> getClientesHome() {
		return clientesHome;
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

	public ResourceBundle getBundle() {
		return bundle;
	}

	public void setBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}

}