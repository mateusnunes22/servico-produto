package br.com.dynamic.relatorio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.com.dynamic.entidade.Funcionario;
import br.com.dynamic.entidade.Registro;
import br.com.dynamic.estrutura.BeanMedio;
import br.com.dynamic.estrutura.FuncionarioRelatorio;
import br.com.dynamic.infra.factories.ComparatorUtil;
import br.com.dynamic.repo.interfaces.IFuncionarioRepo;
import br.com.dynamic.repo.interfaces.IRelatorioRepo;

@ManagedBean
@SessionScoped
public class FuncionarioBeanRpt {
	
	@ManagedProperty(value="#{relatorioRepo}")
	private IRelatorioRepo relatorioRepo;
	
	@ManagedProperty(value="#{funcionarioRepo}")
	private IFuncionarioRepo funcionarioRepo;
	
	private Funcionario funcionario;
	private List<Funcionario> funcionarios;
	
	private BeanMedio rpt = new BeanMedio();
	private List<FuncionarioRelatorio> relatorio =  new ArrayList<FuncionarioRelatorio>();
	private List<Registro> relatorioDetalhado =  new ArrayList<Registro>();
	
	public void init() {
		funcionarios = funcionarioRepo.getAll();
		Collections.sort(funcionarios, ComparatorUtil.getFuncionarioComparator());
	}

	public String atualizar() {
		if (funcionario != null) {
			rpt.setNumero(funcionario.getId());
		}
		relatorio = relatorioRepo.relatorioFuncionarioTotal(rpt);

		return null;
	}
	
	public String detalharVenda(FuncionarioRelatorio funcionarioRelatorio) {
		rpt.setTexto(funcionarioRelatorio.getNome());
		return "detalhar";
	}
	
	public List<FuncionarioRelatorio> getRelatorio() {
		return relatorio;
	}
	
	public List<Registro> getRelatorioDetalhado() {
		relatorioDetalhado = relatorioRepo.relatorioFuncionarioDetalhado(rpt);
		return relatorioDetalhado;
	}

	public void setRpt(BeanMedio rpt) {
		this.rpt = rpt;
	}

	public BeanMedio getRpt() {
		return rpt;
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

	public IRelatorioRepo getRelatorioRepo() {
		return relatorioRepo;
	}

	public void setRelatorioRepo(IRelatorioRepo relatorioRepo) {
		this.relatorioRepo = relatorioRepo;
	}

	public IFuncionarioRepo getFuncionarioRepo() {
		return funcionarioRepo;
	}

	public void setFuncionarioRepo(IFuncionarioRepo funcionarioRepo) {
		this.funcionarioRepo = funcionarioRepo;
	}

}