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
import br.com.dynamic.entidade.Servico;
import br.com.dynamic.estrutura.Bean;
import br.com.dynamic.infra.factories.ComparatorUtil;
import br.com.dynamic.repo.interfaces.ICategoriaRepo;
import br.com.dynamic.repo.interfaces.IServicoRepo;
import br.com.dynamic.util.SessionUtil;

@ManagedBean
@SessionScoped
public class ServicoBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value="#{servicoRepo}")
	private IServicoRepo servicoRepo;
	
	@ManagedProperty(value="#{categoriaRepo}")
	private ICategoriaRepo categoriaRepo;
	
	@ManagedProperty("#{msg}")
	private ResourceBundle bundle;
	
	private List<Categoria> categorias;
	
	private Servico servico = new Servico();
	private Bean servicoList = new Bean();
	public List<Servico> lista = new ArrayList<Servico>();
	public List<Servico> listaForgId = new ArrayList<Servico>();
	
	public void init() {
		categorias = categoriaRepo.getAll();
		Collections.sort(categorias, ComparatorUtil.getCategoriaComparator());
	}
	
	public String atualizar() {
		lista = servicoRepo.listarPorNome(servicoList.getTexto());
		Collections.sort(lista, ComparatorUtil.getServicoComparator());
		servicoList.setSize(lista.size());
		
		return null;
	}

	public void verificarNome() {
		Servico servicoNome = servicoRepo.buscarNomeLista(servico.getNome());

		if (servicoNome != null) {
			Logger.getLogger(getClass().getName()).log(Level.WARN, bundle.getString("servico.erro.nome.duplicado"));
			FacesContext.getCurrentInstance().addMessage("consulta:id",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("servico.erro.nome.duplicado"), ""));
		}
	}

	public String salvar() {
		String retorno = null;
		
		try {
			if (servico.getId() == null) {
				servicoRepo.add(servico);
			} else {
				servicoRepo.update(servico);
				retorno = "edit";
			}
			servico = new Servico();
			SessionUtil.addSuccessMessage("OperacaoSucesso");
		} catch (PersistenceException e) {
			Logger.getLogger(getClass().getName()).log(Level.WARN, bundle.getString("default.erro.validacao"), e);
			SessionUtil.addErrorMessage("OperacaoFracasso");
		}

		return retorno;
	}

	public String editar(Servico servico) {
		this.servico = servico;

		return "edit";
	}

	public List<Servico> getLista() {
		return lista;
	}

	public void setListaForgId(List<Servico> listaForgId) {
		this.listaForgId = listaForgId;
	}

	public void setLista(List<Servico> lista) {
		this.lista = lista;
	}

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public void setServicoList(Bean servicoList) {
		this.servicoList = servicoList;
	}

	public Bean getServicoList() {
		return servicoList;
	}

	public String novo() {
		servico = new Servico();
		return "novo";
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public IServicoRepo getServicoRepo() {
		return servicoRepo;
	}

	public void setServicoRepo(IServicoRepo servicoRepo) {
		this.servicoRepo = servicoRepo;
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
