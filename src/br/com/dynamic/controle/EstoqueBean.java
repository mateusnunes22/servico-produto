package br.com.dynamic.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.com.dynamic.entidade.Estoque;
import br.com.dynamic.entidade.Produto;
import br.com.dynamic.estrutura.BeanMedio;
import br.com.dynamic.infra.factories.ComparatorUtil;
import br.com.dynamic.repo.interfaces.IEstoqueRepo;
import br.com.dynamic.repo.interfaces.IProdutoRepo;
import br.com.dynamic.util.SessionUtil;

@ManagedBean
@ViewScoped
public class EstoqueBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value="#{estoqueRepo}")
	private IEstoqueRepo estoqueRepo;
	
	@ManagedProperty(value="#{produtoRepo}")
	private IProdutoRepo produtoRepo;
	
	private Produto produto;
	private List<Produto> produtos;
	
	private BeanMedio estoqueList = new BeanMedio();
	public List<Estoque> lista =  new ArrayList<Estoque>();
	public List<Estoque> listaEstoqueBaixo =  new ArrayList<Estoque>();
	
	@PostConstruct
	public void init() {
		produtos = produtoRepo.getAll();
		listaEstoqueBaixo = estoqueRepo.estoqueBaixo();
		estoqueList.setSize(listaEstoqueBaixo.size());
		
		Collections.sort(listaEstoqueBaixo, ComparatorUtil.getEstoqueComparator());
		Collections.sort(produtos, ComparatorUtil.getProdutoComparator());
	}
	
	public String atualizar() {
		if (produto == null) {
			lista = estoqueRepo.getAll();
		} else {
			lista = estoqueRepo.findAllByProduto(produto);
		}
		
		Collections.sort(lista, ComparatorUtil.getEstoqueComparator());
		estoqueList.setSize2(lista.size());

		return null;
	}

	public String baixarEstoque(Estoque estoque) {
		estoqueRepo.baixarEstoque(estoque);
		SessionUtil.addSuccessMessage("OperacaoSucesso");	

		return null;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public List<Estoque> getLista() {
		return lista;
	}

	public List<Estoque> getListaEstoqueBaixo() {
		return listaEstoqueBaixo;
	}

	public void setListaEstoqueBaixo(List<Estoque> listaEstoqueBaixo) {
		this.listaEstoqueBaixo = listaEstoqueBaixo;
	}

	public void setLista(List<Estoque> lista) {
		this.lista = lista;
	}

	public void setEstoqueList(BeanMedio estoqueList) {
		this.estoqueList = estoqueList;
	}

	public BeanMedio getEstoqueList() {
		return estoqueList;
	}

	public IEstoqueRepo getEstoqueRepo() {
		return estoqueRepo;
	}

	public void setEstoqueRepo(IEstoqueRepo estoqueRepo) {
		this.estoqueRepo = estoqueRepo;
	}

	public IProdutoRepo getProdutoRepo() {
		return produtoRepo;
	}

	public void setProdutoRepo(IProdutoRepo produtoRepo) {
		this.produtoRepo = produtoRepo;
	}

}