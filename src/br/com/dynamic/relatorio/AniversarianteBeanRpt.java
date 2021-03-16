package br.com.dynamic.relatorio;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.com.dynamic.entidade.Cliente;
import br.com.dynamic.repo.interfaces.IClienteRepo;
import br.com.dynamic.repo.interfaces.IConfiguracaoRepo;

@ManagedBean
@ViewScoped
public class AniversarianteBeanRpt implements Serializable {
	
	@ManagedProperty(value="#{configuracaoRepo}")
	private IConfiguracaoRepo configuracaoRepo;
	
	@ManagedProperty(value="#{clienteRepo}")
	private IClienteRepo clienteRepo;
	
	public List<Cliente> relatorio;

	@PostConstruct
	public void init() {
		int numeroDias = configuracaoRepo.getNumeroDias();
		relatorio = clienteRepo.listarAniversariantes(numeroDias);
	}

	public IConfiguracaoRepo getConfiguracaoRepo() {
		return configuracaoRepo;
	}

	public void setConfiguracaoRepo(IConfiguracaoRepo configuracaoRepo) {
		this.configuracaoRepo = configuracaoRepo;
	}

	public IClienteRepo getClienteRepo() {
		return clienteRepo;
	}

	public void setClienteRepo(IClienteRepo clienteRepo) {
		this.clienteRepo = clienteRepo;
	}

	public List<Cliente> getRelatorio() {
		return relatorio;
	}
	
}