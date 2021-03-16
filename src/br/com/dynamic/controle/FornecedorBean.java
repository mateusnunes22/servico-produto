package br.com.dynamic.controle;

import java.io.Serializable;
import java.util.ArrayList;
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

import br.com.dynamic.entidade.Fornecedor;
import br.com.dynamic.estrutura.Bean;
import br.com.dynamic.repo.interfaces.IFornecedorRepo;
import br.com.dynamic.util.SessionUtil;

@ManagedBean
@SessionScoped
public class FornecedorBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value="#{fornecedorRepo}")
	private IFornecedorRepo fornecedorRepo;
	
	@ManagedProperty("#{msg}")
	private ResourceBundle bundle;

	private Fornecedor fornecedor = new Fornecedor();
	private Bean fornecedorList = new Bean();
	public List<Fornecedor> lista =  new ArrayList<Fornecedor>();

	public String atualizar() {
		lista = fornecedorRepo.findAllByNome(fornecedorList.getTexto());
		fornecedorList.setSize(lista.size());

		return null;
	}

	public void verificarNome() {
		Fornecedor fornecedorNome = fornecedorRepo.findByNome(fornecedor.getNome());

		if (fornecedorNome != null) {
			Logger.getLogger(getClass().getName()).log(Level.WARN, bundle.getString("fornecedor.erro.nome.duplicado"));
			FacesContext.getCurrentInstance().addMessage("consulta:id",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("fornecedor.erro.nome.duplicado"), ""));
		}
	}

	public String salvar() {
		String retorno = null;
		
		try {
			if (fornecedor.getId() == null) {
				fornecedorRepo.add(fornecedor);
			} else {
				fornecedorRepo.update(fornecedor);
				retorno = "edit";
			}
			fornecedor = new Fornecedor();
			SessionUtil.addSuccessMessage("OperacaoSucesso");
		} catch (PersistenceException e) {
			Logger.getLogger(getClass().getName()).log(Level.WARN,  bundle.getString("default.erro.validacao"), e);
			SessionUtil.addErrorMessage("OperacaoFracasso");
		}

		return retorno;
	}

	public String editar(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
		return "edit";
	}

	public List<Fornecedor> getLista() {
		return lista;
	}

	public void setLista(List<Fornecedor> lista) {
		this.lista = lista;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public void setFornecedorList(Bean fornecedorList) {
		this.fornecedorList = fornecedorList;
	}

	public Bean getFornecedorList() {
		return fornecedorList;
	}

	public String novo() {
		fornecedor = new Fornecedor();
		return "edit";
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