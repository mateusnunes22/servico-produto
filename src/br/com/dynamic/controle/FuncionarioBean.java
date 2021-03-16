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

import br.com.dynamic.entidade.Funcionario;
import br.com.dynamic.estrutura.Bean;
import br.com.dynamic.infra.factories.ComparatorUtil;
import br.com.dynamic.repo.interfaces.IFuncionarioRepo;
import br.com.dynamic.util.SessionUtil;

@ManagedBean
@SessionScoped
public class FuncionarioBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{funcionarioRepo}")
	private IFuncionarioRepo funcionarioRepo;
	
	@ManagedProperty("#{msg}")
	private ResourceBundle bundle;
	
	private Funcionario funcionario = new Funcionario();
	private Bean funcionarioList = new Bean();
	public List<Funcionario> lista =  new ArrayList<Funcionario>();

	public String atualizar() {
		lista = funcionarioRepo.findAllByNome(funcionarioList.getTexto());
		Collections.sort(lista, ComparatorUtil.getFuncionarioComparator());
		funcionarioList.setSize(lista.size());

		return null;
	}

	public void verificarNome() {
		Funcionario funcionarioNome = funcionarioRepo.findByNome(funcionario.getNome());
		
		if (funcionarioNome != null) {
			Logger.getLogger(getClass().getName()).log(Level.WARN, bundle.getString("funcionario.erro.nome.duplicado"));
			FacesContext.getCurrentInstance().addMessage("consulta:id",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("funcionario.erro.nome.duplicado"), ""));
		}
	}

	public String salvar() {
		String retorno = null;
		
		try {
			if (funcionario.getId() == null) {
				funcionarioRepo.add(funcionario);
			} else {
				funcionarioRepo.update(funcionario);
				retorno = "edit";
			}
			funcionario = new Funcionario();
			SessionUtil.addSuccessMessage("OperacaoSucesso");
		} catch (PersistenceException e) {
			Logger.getLogger(getClass().getName()).log(Level.WARN,  bundle.getString("default.erro.validacao"), e);
			SessionUtil.addErrorMessage("OperacaoFracasso");
		}

		return retorno;
	}

	public String editar(Funcionario funcionario) {
		this.funcionario = funcionario;

		return "edit";
	}

	public String novo() throws IllegalStateException {
		funcionario = new Funcionario();
		
		return "edit";
	}

	public List<Funcionario> getLista() {
		return lista;
	}

	public void setLista(List<Funcionario> lista) {
		this.lista = lista;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public void setFuncionarioList(Bean funcionarioList) {
		this.funcionarioList = funcionarioList;
	}

	public Bean getFuncionarioList() {
		return funcionarioList;
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