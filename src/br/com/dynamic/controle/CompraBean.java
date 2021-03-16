package br.com.dynamic.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.persistence.PersistenceException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import br.com.dynamic.entidade.Fornecedor;
import br.com.dynamic.entidade.Produto;
import br.com.dynamic.entidade.Registro;
import br.com.dynamic.infra.factories.ComparatorUtil;
import br.com.dynamic.repo.interfaces.IFornecedorRepo;
import br.com.dynamic.repo.interfaces.IProdutoRepo;
import br.com.dynamic.repo.interfaces.IRegistroRepo;
import br.com.dynamic.util.SessionUtil;

@ManagedBean
@SessionScoped
public class CompraBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value="#{registroRepo}")
	private IRegistroRepo compraRepo;
	
	@ManagedProperty(value="#{produtoRepo}")
	private IProdutoRepo produtoRepo;
	
	@ManagedProperty(value="#{fornecedorRepo}")
	private IFornecedorRepo fornecedorRepo;
	
	@ManagedProperty("#{msg}")
	private ResourceBundle bundle;
	
	private List<Fornecedor> fornecedores;
	private List<Produto> produtos;

	private Registro compra = new Registro();
	private int quantidade = 1;
	public List<Registro> lista =  new ArrayList<Registro>();
	public List<Registro> listaForgId =  new ArrayList<Registro>();
	public List<Registro> listaNomeLike =  new ArrayList<Registro>();
	
	public void init() {
		fornecedores = fornecedorRepo.getAll();
		produtos = produtoRepo.getAll();
		
		Collections.sort(fornecedores, ComparatorUtil.getFornecedorComparator());
		Collections.sort(produtos, ComparatorUtil.getProdutoComparator());
	}

	public String salvar() {
		try {
			for (int i=0; i<quantidade; i++) {
				compraRepo.comprar(compra);
			}
			
			SessionUtil.addSuccessMessage("OperacaoSucesso");	
			compra = new Registro();
			quantidade = 1;
		} catch (PersistenceException e) {
			Logger.getLogger(getClass().getName()).log(Level.WARN,  bundle.getString("default.erro.validacao"), e);
			SessionUtil.addErrorMessage("OperacaoFracasso");
		}

		return null;	
	}

	public String remover() {
		lista.clear();
		return null;
	}
	
	public void produtoChanged() {
		if (compra.getProduto() != null) {
			compra.setValor(compra.getProduto().getValorCompra());
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

	public Registro getCompra() {
		return compra;
	}

	public void setCompra(Registro compra) {
		this.compra = compra;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public List<Fornecedor> getFornecedores() {
		return fornecedores;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public IRegistroRepo getCompraRepo() {
		return compraRepo;
	}

	public void setCompraRepo(IRegistroRepo compraRepo) {
		this.compraRepo = compraRepo;
	}

	public IProdutoRepo getProdutoRepo() {
		return produtoRepo;
	}

	public void setProdutoRepo(IProdutoRepo produtoRepo) {
		this.produtoRepo = produtoRepo;
	}

	public IFornecedorRepo getFornecedorRepo() {
		return fornecedorRepo;
	}

	public void setFornecedorRepo(IFornecedorRepo fornecedorRepo) {
		this.fornecedorRepo = fornecedorRepo;
	}

	public ResourceBundle getBundle() {
		return bundle;
	}

	public void setBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}

}