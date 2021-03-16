package br.com.dynamic.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.dynamic.entidade.Categoria;
import br.com.dynamic.entidade.Cliente;
import br.com.dynamic.entidade.Funcionario;
import br.com.dynamic.entidade.Registro;
import br.com.dynamic.entidade.Servico;
import br.com.dynamic.infra.factories.ComparatorUtil;
import br.com.dynamic.repo.interfaces.ICategoriaRepo;
import br.com.dynamic.repo.interfaces.IFuncionarioRepo;
import br.com.dynamic.repo.interfaces.IRegistroRepo;
import br.com.dynamic.repo.interfaces.IServicoRepo;

@ManagedBean
@ViewScoped
public class AgendamentoBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value="#{registroRepo}")
	private IRegistroRepo agendamentoRepo;
	
	@ManagedProperty(value="#{categoriaRepo}")
	private ICategoriaRepo categoriaRepo;
	
	@ManagedProperty(value="#{servicoRepo}")
	private IServicoRepo servicoRepo;
	
	@ManagedProperty(value="#{funcionarioRepo}")
	private IFuncionarioRepo funcionarioRepo;

	private Categoria categoria;
	
	private List<Categoria> categorias;
	private List<Funcionario> funcionarios;
	private List<Servico> servicos;
	
	private Registro agendamento;
	private float total = 0;
	public List<Registro> lista =  new ArrayList<Registro>();
	public List<Registro> listaForgId =  new ArrayList<Registro>();
	
	@PostConstruct
	public void init() {
		agendamento = new Registro();
		agendamento.setData(new Date());

		categorias = categoriaRepo.getAll();
		funcionarios = funcionarioRepo.getAll();
		
		Collections.sort(categorias, ComparatorUtil.getCategoriaComparator());
		Collections.sort(funcionarios, ComparatorUtil.getFuncionarioComparator());
	}

	public String salvar()  {
		HttpSession session2 = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		Cliente cliente = (Cliente) session2.getAttribute("clienteAgendamento");
		agendamento.setCliente(cliente);
		agendamentoRepo.agendar(agendamento); 

		return null;	
	}

	public String finalizar() {
		lista.clear();

		return null;
	}

	public String remover(Registro registro) {
		agendamentoRepo.removerVendaServico(registro);

		return null;					
	}
	
	public void categoriaChanged() {
		agendamento.setServico(null);
		agendamento.setValor(0f);
		servicos = servicoRepo.listarPorCategoria(categoria);
		Collections.sort(servicos, ComparatorUtil.getServicoComparator());
	}

	public void servicoChanged() {
		Float valor = (agendamento.getServico() != null) ? agendamento.getServico().getValor() : 0f; 
		agendamento.setValor(valor);
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public List<Registro> getLista() {
		return agendamentoRepo.BuscarAgendamento(new Date());
	}

	public void setListaForgId(List<Registro> listaForgId) {
		this.listaForgId = listaForgId;
	}

	public void setLista(List<Registro> lista) {
		this.lista = lista;
	}

	public Registro getAgendamento() {
		return agendamento;
	}

	public void setAgendamento(Registro venda) {
		this.agendamento = venda;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public float getTotal() {
		return total;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public List<Servico> getServicos() {
		return servicos;
	}

	public IRegistroRepo getAgendamentoRepo() {
		return agendamentoRepo;
	}

	public void setAgendamentoRepo(IRegistroRepo agendamentoRepo) {
		this.agendamentoRepo = agendamentoRepo;
	}

	public ICategoriaRepo getCategoriaRepo() {
		return categoriaRepo;
	}

	public void setCategoriaRepo(ICategoriaRepo categoriaRepo) {
		this.categoriaRepo = categoriaRepo;
	}

	public IServicoRepo getServicoRepo() {
		return servicoRepo;
	}

	public void setServicoRepo(IServicoRepo servicoRepo) {
		this.servicoRepo = servicoRepo;
	}

	public IFuncionarioRepo getFuncionarioRepo() {
		return funcionarioRepo;
	}

	public void setFuncionarioRepo(IFuncionarioRepo funcionarioRepo) {
		this.funcionarioRepo = funcionarioRepo;
	}

}