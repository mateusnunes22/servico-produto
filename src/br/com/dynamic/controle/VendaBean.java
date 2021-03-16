package br.com.dynamic.controle;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.dynamic.entidade.Categoria;
import br.com.dynamic.entidade.Cliente;
import br.com.dynamic.entidade.Funcionario;
import br.com.dynamic.entidade.Produto;
import br.com.dynamic.entidade.Registro;
import br.com.dynamic.entidade.Servico;
import br.com.dynamic.infra.factories.ComparatorUtil;
import br.com.dynamic.repo.interfaces.ICategoriaRepo;
import br.com.dynamic.repo.interfaces.IFuncionarioRepo;
import br.com.dynamic.repo.interfaces.IProdutoRepo;
import br.com.dynamic.repo.interfaces.IRegistroRepo;
import br.com.dynamic.repo.interfaces.IServicoRepo;
import br.com.dynamic.util.SessionUtil;

@ManagedBean
@SessionScoped
public class VendaBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value="#{registroRepo}")
	private IRegistroRepo vendaRepo;
	
	@ManagedProperty(value="#{categoriaRepo}")
	private ICategoriaRepo categoriaRepo;
	
	@ManagedProperty(value="#{funcionarioRepo}")
	private IFuncionarioRepo funcionarioRepo;

	@ManagedProperty(value="#{produtoRepo}")
	private IProdutoRepo produtoRepo;
	
	@ManagedProperty(value="#{servicoRepo}")
	private IServicoRepo servicoRepo;
	
	private Categoria categoria;
	
	private List<Categoria> categorias;
	private List<Funcionario> funcionarios;
	private List<Produto> produtos;
	private List<Servico> servicos;
	
	private Registro venda = new Registro();
	private Registro registro;
	private Date data = new Date();
	private Date hora = new Time(new Date().getTime()-10800000);
	private float total = 0;
	private Float valorAutomatico = 0f;
	private String obsevacao;
	public List<Registro> lista =  new ArrayList<Registro>();
	public List<Registro> listaForgId =  new ArrayList<Registro>();
	public List<Registro> listaNomeLike =  new ArrayList<Registro>();
 	
	public void init() {
		if (registro == null) {
			registro = new Registro();
			registro.setData(new Date());
			registro.setHora(new Time(new Date().getTime()-10800000));
		}
		
		categorias = categoriaRepo.getAll();
		funcionarios = funcionarioRepo.getAll();
		
		Collections.sort(categorias, ComparatorUtil.getCategoriaComparator());
		Collections.sort(funcionarios, ComparatorUtil.getFuncionarioComparator());
	}
	
	public String atualizar() {
		return null;
	}
	
	public String dinheiro() {
		registro.setFormaPagamento("Dinheiro");
		salvar();
		return null;
	}
	
	public String debito() {
		registro.setFormaPagamento("Débito");
		salvar();
		return null;
	}
	
	public String credito() {
		registro.setFormaPagamento("Crédito");
		salvar();
		return null;
	}
	
	public String dinheiroRetroativo() {
		venda.setFormaPagamento("Dinheiro");
		salvarRetroativo();
		return null;
	}
	
	public String debitoRetroativo() {
		venda.setFormaPagamento("Débito");
		salvarRetroativo();
		return null;
	}
	
	public String creditoRetroativo() {
		venda.setFormaPagamento("Crédito");
		salvarRetroativo();
		return null;
	}

	public String salvar() {
		if (registro.getServico() == null && registro.getProduto() == null) {
			SessionUtil.addErrorMessage("ServicoProdutoObrigatorio");
			return null;
		}
		
		registro.setValor(valorAutomatico);
		
		HttpSession session2 = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		Cliente cliente = (Cliente) session2.getAttribute("clienteVenda");
		
		Registro vendaLista = new Registro(registro);
		vendaLista.setCliente(cliente);
		
		Servico produto = new Servico();
		
		Registro vendaAdd = new Registro();		
		vendaAdd = vendaRepo.vender(vendaLista);
		
		if (registro.getProduto() != null && !registro.getProduto().getNome().equalsIgnoreCase("")) {
			produto.setNome(registro.getProduto().getNome());
			produto.setValor(registro.getValor());

			vendaLista.setServico(produto);
		} 
		
		vendaAdd.getId().setIdVenda(vendaRepo.getInsertedId());
		lista.add(vendaAdd);
		
		venda = new Registro();
		data = new Date();
		hora = new Time(new Date().getTime()-10800000);
		
		registro.setId(null);
		registro.setProduto(null);
		registro.setServico(null);
		registro.setValor(null);
		registro.setData(new Date());
		registro.setHora(new Time(new Date().getTime()-10800000));
		total = 0;
		
		for (Registro parte : lista) {
			total += parte.getValor();
		}
		
//		produtos.clear();
//		servicos.clear();
		valorAutomatico = 0f;

		return null;
	}
	
	public String salvarRetroativo() {
		if (venda.getServico() == null && venda.getProduto() == null) {
			SessionUtil.addErrorMessage("ServicoProdutoObrigatorio");
			return null;
		}
		
		venda.setValor(valorAutomatico);
		
		HttpSession session2 = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		Cliente cliente = (Cliente) session2.getAttribute("clienteVenda");
		
		Registro vendaLista = new Registro(venda);
		vendaLista.setCliente(cliente);
		Servico produto = new Servico();
		
		if (!venda.getProduto().getNome().equalsIgnoreCase(""))
		{
			produto.setNome(venda.getProduto().getNome());
			produto.setValor(venda.getValor());
			vendaLista.setServico(produto);				
		}
		
		Registro vendaAdd = new Registro();
		vendaAdd = vendaRepo.venderRetroativo(vendaLista, cliente.getId(), data); 
		
		if (!venda.getProduto().getNome().equalsIgnoreCase(""))
		{
			vendaAdd.setServico(produto);
		}
		
		lista.add(vendaAdd);
		
		venda = new Registro();
		data = new Date();
		hora = new Date();
		total = 0;
		
		for (Registro parte : lista) {
			total += parte.getValor();
		}
		
		registro.setProduto(null);
		registro.setServico(null);
		valorAutomatico = 0f;

		return null;
	}

	public String finalizar() {
		lista.clear();
		
		venda = new Registro();
		data = new Date();
		hora = new Date();
		
		registro.setFuncionario(null);
		registro.setProduto(null);
		registro.setServico(null);
		valorAutomatico = 0f;
		
		return "home";
	}

	public String remover(Registro servicoEdit) {
		total = total - servicoEdit.getValor();
		vendaRepo.removerVendaServico(vendaRepo.getById(servicoEdit.getId().getIdVenda()));
		lista.remove(servicoEdit);

		return null;
	}
	
	public String removerRetroativo(Registro servicoEdit) {
		total = total - servicoEdit.getValor();
		vendaRepo.removerVendaServico(servicoEdit);
		lista.remove(servicoEdit);
		
		return null;		
	}
	
	public void categoriaChanged() {
		registro.setServico(null);
		registro.setProduto(null);		
		valorAutomatico = 0f;
		
		servicos = servicoRepo.listarPorCategoria(categoria);
		Collections.sort(servicos, ComparatorUtil.getServicoComparator());
		
		produtos = produtoRepo.findAllByCategoria(categoria);
		Collections.sort(produtos, ComparatorUtil.getProdutoComparator());
	}

	public void servicoChanged() {
		registro.setProduto(null);
		valorAutomatico = 0f;
		
		if (registro.getServico() != null) {
			valorAutomatico = registro.getServico().getValor();
		}
	}

	public void produtoChanged() {
		registro.setServico(null);
		valorAutomatico = 0f;
	
		if (registro.getProduto() != null) {
			valorAutomatico = registro.getProduto().getValorVenda();
		}
	}

	public List<Registro> getLista() {
		return lista;
	}

	public void setListaForgId(List<Registro> listaForgId) {
		this.listaForgId = listaForgId;
	}

	public void setLista(List<Registro> lista) {
		this.lista = lista;
	}

	public Registro getVenda() {
		return venda;
	}

	public void setVenda(Registro venda) {
		this.venda = venda;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public float getTotal() {
		return total;
	}

	public void setValorAutomatico(float valorAutomatico) {
		this.valorAutomatico = valorAutomatico;
	}

	public float getValorAutomatico() {
		return valorAutomatico;
	}

	public void setObsevacao(String obsevacao) {
		this.obsevacao = obsevacao;
	}

	public String getObsevacao() {
		return obsevacao;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Date getData() {
		return data;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public Date getHora() {
		return hora;
	}
	
	public ICategoriaRepo getCategoriaRepo() {
		return categoriaRepo;
	}

	public void setCategoriaRepo(ICategoriaRepo categoriaRepo) {
		this.categoriaRepo = categoriaRepo;
	}

	public IRegistroRepo getVendaRepo() {
		return vendaRepo;
	}

	public void setVendaRepo(IRegistroRepo vendaRepo) {
		this.vendaRepo = vendaRepo;
	}

	public IServicoRepo getServicoRepo() {
		return servicoRepo;
	}

	public void setServicoRepo(IServicoRepo servicoRepo) {
		this.servicoRepo = servicoRepo;
	}

	public IProdutoRepo getProdutoRepo() {
		return produtoRepo;
	}

	public void setProdutoRepo(IProdutoRepo produtoRepo) {
		this.produtoRepo = produtoRepo;
	}

	public IFuncionarioRepo getFuncionarioRepo() {
		return funcionarioRepo;
	}

	public void setFuncionarioRepo(IFuncionarioRepo funcionarioRepo) {
		this.funcionarioRepo = funcionarioRepo;
	}

	public Registro getRegistro() {
		return registro;
	}

	public void setRegistro(Registro registro) {
		this.registro = registro;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public List<Servico> getServicos() {
		return servicos;
	}
	
	public List<Produto> getProdutos() {
		return produtos;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

}