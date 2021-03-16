package br.com.dynamic.relatorio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.com.dynamic.entidade.Funcionario;
import br.com.dynamic.entidade.Registro;
import br.com.dynamic.infra.factories.ComparatorUtil;
import br.com.dynamic.repo.interfaces.IFuncionarioRepo;
import br.com.dynamic.repo.interfaces.IRelatorioRepo;

@ManagedBean
@SessionScoped
public class AgendamentoBeanRpt implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value="#{relatorioRepo}")
	private IRelatorioRepo relatorioRepo;
	
	@ManagedProperty(value="#{funcionarioRepo}")
	private IFuncionarioRepo funcionarioRepo;
	
	private Funcionario funcionario;
	private List<Funcionario> funcionarios;
	
	private Date dataRelatorio = new Date();
	public List<Registro> relatorio =  new ArrayList<Registro>();
	
	public void init() {
		funcionarios = funcionarioRepo.getAll();
		Collections.sort(funcionarios, ComparatorUtil.getFuncionarioComparator());
	}
	
	public String atualizar() {
		this.relatorio = relatorioRepo.relatorioAgendamento(dataRelatorio, funcionario);
		return null;
	}
	
	public List<Registro> getRelatorio() {
		return this.relatorio;
	}

	public void setRelatorio(List<Registro> relatorio) {
		this.relatorio = relatorio;
	}

	public void setDataRelatorio(Date dataRelatorio) {
		this.dataRelatorio = dataRelatorio;
	}

	public Date getDataRelatorio() {
		return dataRelatorio;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public IFuncionarioRepo getFuncionarioRepo() {
		return funcionarioRepo;
	}

	public void setFuncionarioRepo(IFuncionarioRepo funcionarioRepo) {
		this.funcionarioRepo = funcionarioRepo;
	}

	public IRelatorioRepo getRelatorioRepo() {
		return relatorioRepo;
	}

	public void setRelatorioRepo(IRelatorioRepo relatorioRepo) {
		this.relatorioRepo = relatorioRepo;
	}

}