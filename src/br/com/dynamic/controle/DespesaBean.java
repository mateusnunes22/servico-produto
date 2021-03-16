package br.com.dynamic.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.persistence.PersistenceException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import br.com.dynamic.entidade.Registro;
import br.com.dynamic.repo.interfaces.IFuncionarioRepo;
import br.com.dynamic.repo.interfaces.IProdutoRepo;
import br.com.dynamic.repo.interfaces.IRegistroRepo;
import br.com.dynamic.repo.interfaces.IServicoRepo;
import br.com.dynamic.util.SessionUtil;

@ManagedBean
@SessionScoped
public class DespesaBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value="#{registroRepo}")
	private IRegistroRepo despesaRepo;
	
	@ManagedProperty(value="#{servicoRepo}")
	private IServicoRepo servicoRepo;
	
	@ManagedProperty(value="#{produtoRepo}")
	private IProdutoRepo produtoRepo;
	
	@ManagedProperty(value="#{funcionarioRepo}")
	private IFuncionarioRepo funcionarioRepo;
	
	@ManagedProperty("#{msg}")
	private ResourceBundle bundle;
	
	private Registro despesa = new Registro();
	public List<Registro> lista =  new ArrayList<Registro>();
	public List<SelectItem> produtos =  new ArrayList<SelectItem>();

	public String atualizar() {
		return null;	
	}

	public String salvar() {
		try {
			despesaRepo.despesa(despesa);
			despesa = new Registro();
			SessionUtil.addSuccessMessage("OperacaoSucesso");
		} catch (PersistenceException e) {
			Logger.getLogger(getClass().getName()).log(Level.WARN,  bundle.getString("default.erro.validacao"), e);
			SessionUtil.addErrorMessage("OperacaoFracasso");
		}

		return null;	
	}

	public String finalizar() {
		lista.clear();

		return null;
	}

	public String remover() {
		lista.clear();

		return null;
	}

	public List<Registro> getLista() {
		return lista;
	}

	public void setLista(List<Registro> lista) {
		this.lista = lista;
	}

	public void setDespesa(Registro despesa) {
		this.despesa = despesa;
	}

	public Registro getDespesa() {
		return despesa;
	}

	public IRegistroRepo getDespesaRepo() {
		return despesaRepo;
	}

	public void setDespesaRepo(IRegistroRepo despesaRepo) {
		this.despesaRepo = despesaRepo;
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

	public ResourceBundle getBundle() {
		return bundle;
	}

	public void setBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}

}