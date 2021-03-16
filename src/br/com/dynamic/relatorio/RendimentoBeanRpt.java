package br.com.dynamic.relatorio;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.com.dynamic.estrutura.Bean;
import br.com.dynamic.estrutura.Rendimento;
import br.com.dynamic.repo.interfaces.IRelatorioRepo;

@ManagedBean
@SessionScoped
public class RendimentoBeanRpt {
	
	@ManagedProperty(value="#{relatorioRepo}")
	private IRelatorioRepo relatorioRepo;
	
	private Bean rpt = new Bean();
	public List<Rendimento> relatorio =  new ArrayList<Rendimento>();
	
	public String atualizar() {
		relatorio = relatorioRepo.relatorioRendimento(rpt);
		return null;
	}
	
	public List<Rendimento> getRelatorio() {
		return relatorio;
	}

	public void setRelatorio(List<Rendimento> relatorio) {
		this.relatorio = relatorio;
	}

	public void setRpt(Bean rpt) {
		this.rpt = rpt;
	}

	public Bean getRpt() {
		return rpt;
	}

	public IRelatorioRepo getRelatorioRepo() {
		return relatorioRepo;
	}

	public void setRelatorioRepo(IRelatorioRepo relatorioRepo) {
		this.relatorioRepo = relatorioRepo;
	}

}