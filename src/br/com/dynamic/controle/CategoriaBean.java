package br.com.dynamic.controle;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.persistence.PersistenceException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import br.com.dynamic.entidade.Categoria;
import br.com.dynamic.estrutura.Bean;
import br.com.dynamic.repo.interfaces.ICategoriaRepo;
import br.com.dynamic.util.SessionUtil;

@ManagedBean
@SessionScoped
public class CategoriaBean {
	
	@ManagedProperty(value="#{categoriaRepo}")
	private ICategoriaRepo categoriaRepo;
	
	@ManagedProperty("#{msg}")
	private ResourceBundle bundle;
	
	private Categoria categoria = new Categoria();
	private Bean categoriaList = new Bean();
	public List<Categoria> lista = new ArrayList<Categoria>();

	public String atualizar() {
		lista = categoriaRepo.findAllByDescricao(categoriaList.getTexto());
		categoriaList.setSize(lista.size());

		return null;
	}

	@Transactional
	public String salvar() {
		String retorno = null;
		
		try {
			if (categoria.getId() == null) {
				categoriaRepo.add(categoria);
			} else {
				categoriaRepo.update(categoria);
				retorno = "edit";
			}
			categoria = new Categoria();
			SessionUtil.addSuccessMessage("OperacaoSucesso");
		} catch (PersistenceException e) {
			Logger.getLogger(getClass().getName()).log(Level.WARN,  bundle.getString("default.erro.validacao"), e);
			SessionUtil.addErrorMessage("OperacaoFracasso");
		}

		return retorno;
	}

	public String editar(Categoria categoria) {
		this.categoria = categoria;
		return "edit";
	}

	public String vender() {
		return "vender";
	}

	public String agendar() {
		return "agendar";
	}

	public List<Categoria> getLista() {
		return lista;
	}

	public void setLista(List<Categoria> lista) {
		this.lista = lista;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Bean getCategoriaList() {
		return categoriaList;
	}

	public void setCategoriaList(Bean categoriaList) {
		this.categoriaList = categoriaList;
	}

	public String novo() throws IllegalStateException {
		categoria = new Categoria();

		return "edit";
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