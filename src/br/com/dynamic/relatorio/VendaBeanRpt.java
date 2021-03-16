package br.com.dynamic.relatorio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.com.dynamic.entidade.Produto;
import br.com.dynamic.entidade.Registro;
import br.com.dynamic.entidade.Servico;
import br.com.dynamic.infra.factories.ComparatorUtil;
import br.com.dynamic.repo.interfaces.IProdutoRepo;
import br.com.dynamic.repo.interfaces.IRegistroRepo;
import br.com.dynamic.repo.interfaces.IRelatorioRepo;
import br.com.dynamic.repo.interfaces.IServicoRepo;

@ManagedBean
@SessionScoped
public class VendaBeanRpt implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value="#{relatorioRepo}")
	private IRelatorioRepo relatorioRepo;
	
	@ManagedProperty(value="#{produtoRepo}")
	private IProdutoRepo produtoRepo;
	
	@ManagedProperty(value="#{servicoRepo}")
	private IServicoRepo servicoRepo;
	
	@ManagedProperty(value="#{registroRepo}")
	private IRegistroRepo registroRepo;
	
	private Date dataInicial = new Date();
	private Date dataFinal = new Date();
	private Produto produto;
	private Servico servico;
	
	private List<Servico> servicos;
	private List<Produto> produtos;
	private List<Registro> detalhamentoProduto;
	private List<Registro> detalhamentoServico;
	
	public List<Produto> relatorioProdutoTotal =  new ArrayList<Produto>();
	public List<Servico> relatorioServicoTotal =  new ArrayList<Servico>();

	public void init() {
		servicos = servicoRepo.getAll();
		produtos = produtoRepo.getAll();

		Collections.sort(servicos, ComparatorUtil.getServicoComparator());
		Collections.sort(produtos, ComparatorUtil.getProdutoComparator());
	}

	public String atualizar() {
		relatorioServicoTotal = relatorioRepo.relatorioVendaServicoTotal(dataInicial, dataFinal, servico);
		Servico servico = relatorioServicoTotal.remove(0);
		Collections.sort(relatorioServicoTotal, ComparatorUtil.getServicoComparatorPorValorDesc());
		relatorioServicoTotal.add(0, servico);
		
		relatorioProdutoTotal = relatorioRepo.relatorioVendaProdutoTotal(dataInicial, dataFinal, produto);
		Produto produto = relatorioProdutoTotal.remove(0);
		Collections.sort(relatorioProdutoTotal, ComparatorUtil.getProdutoComparatorPorValorVendaDesc());
		relatorioProdutoTotal.add(0, produto);
		
		return null;
	}
	
	public String detalharProduto(Produto produto) {
		if (produto.getId() == null) {
			detalhamentoProduto = registroRepo.findAll(dataInicial, dataFinal);
		} else {
			detalhamentoProduto = registroRepo.findAllByProduto(produto, dataInicial, dataFinal);
		}
		return "detalharProduto";
	}
	
	public String detalharServico(Servico servico) {
		if (servico.getId() == null) {
			detalhamentoServico = registroRepo.findAllServicos(dataInicial, dataFinal);
		} else {
			detalhamentoServico = registroRepo.findAllByServico(servico, dataInicial, dataFinal);
		}
		return "detalharServico";
	}

	public List<Produto> getRelatorioProdutoTotal() {
		return relatorioProdutoTotal;
	}

	public List<Servico> getRelatorioServicoTotal() {
		return relatorioServicoTotal;
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

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public List<Registro> getDetalhamentoProduto() {
		return detalhamentoProduto;
	}

	public List<Registro> getDetalhamentoServico() {
		return detalhamentoServico;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<Servico> getServicos() {
		return servicos;
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

	public IServicoRepo getServicoRepo() {
		return servicoRepo;
	}

	public void setServicoRepo(IServicoRepo servicoRepo) {
		this.servicoRepo = servicoRepo;
	}

	public IRegistroRepo getRegistroRepo() {
		return registroRepo;
	}

	public void setRegistroRepo(IRegistroRepo registroRepo) {
		this.registroRepo = registroRepo;
	}

}