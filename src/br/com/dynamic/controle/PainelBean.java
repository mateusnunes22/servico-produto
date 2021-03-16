package br.com.dynamic.controle;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.com.dynamic.repo.interfaces.IClienteRepo;
import br.com.dynamic.repo.interfaces.IConfiguracaoRepo;
import br.com.dynamic.repo.interfaces.IEstoqueRepo;
import br.com.dynamic.repo.interfaces.IRelatorioRepo;

@ManagedBean
@ViewScoped
public class PainelBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value="#{configuracaoRepo}")
	private IConfiguracaoRepo configuracaoRepo;
	
	@ManagedProperty(value="#{clienteRepo}")
	private IClienteRepo clienteRepo;
	
	@ManagedProperty(value="#{estoqueRepo}")
	private IEstoqueRepo estoqueRepo;
	
	@ManagedProperty(value="#{relatorioRepo}")
	private IRelatorioRepo relatorioRepo;

	private int totalAniversarios;
	private int totalEstoqueBaixo;
	
	@PostConstruct
	public void init() {
		int numeroDias = configuracaoRepo.getNumeroDias();
		totalAniversarios = clienteRepo.listarAniversariantes(numeroDias).size();
		totalEstoqueBaixo = estoqueRepo.estoqueBaixo().size();
	}

	public int getTotalAniversarios() {
		return totalAniversarios;
	}

	public void setTotalAniversarios(int totalAniversarios) {
		this.totalAniversarios = totalAniversarios;
	}

	public int getTotalEstoqueEmBaixa() {
		return totalEstoqueBaixo;
	}
	
	public int getTotalAgendamentos() {
		return relatorioRepo.relatorioAgendamento(new Date()).size();
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

	public IEstoqueRepo getEstoqueRepo() {
		return estoqueRepo;
	}

	public void setEstoqueRepo(IEstoqueRepo estoqueRepo) {
		this.estoqueRepo = estoqueRepo;
	}

	public IRelatorioRepo getRelatorioRepo() {
		return relatorioRepo;
	}

	public void setRelatorioRepo(IRelatorioRepo relatorioRepo) {
		this.relatorioRepo = relatorioRepo;
	}

}