package br.com.dynamic.relatorio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.com.dynamic.entidade.Produto;
import br.com.dynamic.entidade.Registro;
import br.com.dynamic.infra.factories.ComparatorUtil;
import br.com.dynamic.repo.interfaces.IProdutoRepo;
import br.com.dynamic.repo.interfaces.IRelatorioRepo;

@ManagedBean
@SessionScoped
public class CompraBeanRpt {
	
	@ManagedProperty(value="#{relatorioRepo}")
	private IRelatorioRepo relatorioRepo;
	
	@ManagedProperty(value="#{produtoRepo}")
	private IProdutoRepo produtoRepo;

	private Produto produto;
	private List<Produto> produtos;

	private Date dataInicial = new Date();
	private Date dataFinal = new Date();
	public List<Produto> relatorioTotal =  new ArrayList<Produto>();
	private List<Registro> detalhamentoProduto;
	
	public void init() {
		produtos = produtoRepo.getAll();
		Collections.sort(produtos, ComparatorUtil.getProdutoComparator());
	}
	
	public String atualizar() {
		this.relatorioTotal = relatorioRepo.relatorioCompraTotal(dataInicial, dataFinal, produto);
		Produto produto = relatorioTotal.remove(0);
		Collections.sort(relatorioTotal, ComparatorUtil.getProdutoComparatorPorValorVendaDesc());
		relatorioTotal.add(0, produto);
		
		return null;
	}
	
	public String detalharCompra(Produto produto) {
		if (produto.getId() == null) {
			detalhamentoProduto = relatorioRepo.relatorioCompraDetalhado(dataInicial, dataFinal);
		} else {
			detalhamentoProduto = relatorioRepo.relatorioCompraDetalhado(produto, dataInicial, dataFinal);
		}
		return "detalhar";
	}
	
	public List<Produto> getRelatorioTotal() {
		return relatorioTotal;
	}

	public void setRelatorioTotal(List<Produto> relatorioTotal) {
		this.relatorioTotal = relatorioTotal;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
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

	public List<Registro> getDetalhamentoProduto() {
		return detalhamentoProduto;
	}

	public IRelatorioRepo getRelatorioRepo() {
		return relatorioRepo;
	}

	public void setRelatorioRepo(IRelatorioRepo relatorioRepo) {
		this.relatorioRepo = relatorioRepo;
	}

	public IProdutoRepo getProdutoRepo() {
		return produtoRepo;
	}

	public void setProdutoRepo(IProdutoRepo produtoRepo) {
		this.produtoRepo = produtoRepo;
	}	

}