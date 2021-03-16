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

import br.com.dynamic.entidade.Categoria;
import br.com.dynamic.entidade.Produto;
import br.com.dynamic.estrutura.Bean;
import br.com.dynamic.infra.factories.ComparatorUtil;
import br.com.dynamic.repo.interfaces.ICategoriaRepo;
import br.com.dynamic.repo.interfaces.IEstoqueRepo;
import br.com.dynamic.repo.interfaces.IProdutoRepo;
import br.com.dynamic.services.IEstoqueService;
import br.com.dynamic.util.SessionUtil;
 
@ManagedBean
@SessionScoped
public class ProdutoBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value="#{produtoRepo}")
	private IProdutoRepo produtoRepo;
	
	@ManagedProperty(value="#{estoqueRepo}")
	private IEstoqueRepo estoqueRepo;
	
	@ManagedProperty(value="#{estoqueService}")
	private IEstoqueService estoqueService;
	
	@ManagedProperty(value="#{categoriaRepo}")
	private ICategoriaRepo categoriaRepo;
	
	@ManagedProperty("#{msg}")
	private ResourceBundle bundle;
	
	private List<Categoria> categorias;
	
	private Produto produto = new Produto();
	private Bean produtoList = new Bean();
	private int quantidadeBaixa = 0;
	public List<Produto> lista =  new ArrayList<Produto>();
	
	public void init() {
		categorias = categoriaRepo.getAll();
		Collections.sort(categorias, ComparatorUtil.getCategoriaComparator());
	}
	
	public String novo() {
		produto = new Produto();
		return "novo";
	}

	public String atualizar() {
		lista = produtoRepo.findAllByNome(produtoList.getTexto());
		Collections.sort(lista, ComparatorUtil.getProdutoComparator());
		produtoList.setSize(lista.size());

		return null;
	}

	public void verificarNome() {
		Produto produtoNome = produtoRepo.findByNome(produto.getNome());

		if (produtoNome != null) {
			Logger.getLogger(getClass().getName()).log(Level.WARN, bundle.getString("produto.erro.nome.duplicado"));
			FacesContext.getCurrentInstance().addMessage("consulta:id",
							new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("produto.erro.nome.duplicado"), ""));
		}
	}

	public String editar(Produto produto) {
		this.produto = produto;
		quantidadeBaixa = estoqueRepo.findByProduto(produto).getQuantidadeBaixa();

		return "edit";
	}

	public String salvar() {
		String retorno = null;
	
		try {
			String ret = estoqueService.armazenarProduto(produto, quantidadeBaixa);
			retorno = (!ret.equalsIgnoreCase("null")) ? ret : null;
			
			produto = new Produto();
			quantidadeBaixa=0;
			SessionUtil.addSuccessMessage("OperacaoSucesso");
		} catch (PersistenceException e) {
			Logger.getLogger(getClass().getName()).log(Level.WARN,  bundle.getString("default.erro.validacao"), e);
			SessionUtil.addErrorMessage("OperacaoFracasso");
		}

		return retorno;
	}

	public List<Produto> getLista() {
		return lista;
	}

	public void setLista(List<Produto> lista) {
		this.lista = lista;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public void setQuantidadeBaixa(int quantidadeBaixa) {
		this.quantidadeBaixa = quantidadeBaixa;
	}

	public int getQuantidadeBaixa() {
		return quantidadeBaixa;
	}

	public void setProdutoList(Bean produtoList) {
		this.produtoList = produtoList;
	}

	public Bean getProdutoList() {
		return produtoList;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public IProdutoRepo getProdutoRepo() {
		return produtoRepo;
	}

	public void setProdutoRepo(IProdutoRepo produtoRepo) {
		this.produtoRepo = produtoRepo;
	}

	public IEstoqueRepo getEstoqueRepo() {
		return estoqueRepo;
	}

	public void setEstoqueRepo(IEstoqueRepo estoqueRepo) {
		this.estoqueRepo = estoqueRepo;
	}

	public IEstoqueService getEstoqueService() {
		return estoqueService;
	}

	public void setEstoqueService(IEstoqueService estoqueService) {
		this.estoqueService = estoqueService;
	}

	public ICategoriaRepo getCategoriaRepo() {
		return categoriaRepo;
	}

	public void setCategoriaRepo(ICategoriaRepo categoriaRepo) {
		this.categoriaRepo = categoriaRepo;
	}

	public ResourceBundle getBundle() {
		return bundle;
	}

	public void setBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}

}