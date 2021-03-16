package br.com.dynamic.relatorio;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.com.dynamic.entidade.Registro;
import br.com.dynamic.repo.interfaces.IRelatorioRepo;

@ManagedBean
@ViewScoped
public class DespesaBeanRpt implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value="#{relatorioRepo}")
	private IRelatorioRepo relatorioRepo;
	
	private Date dataInicial = new Date();
	private Date dataFinal = new Date();
	private List<Registro> relatorioTotal;
	
	public String atualizar() {
		this.relatorioTotal = relatorioRepo.relatorioDespesaTotal(dataInicial, dataFinal);
		return null;
	}
	
	public List<Registro> getRelatorioTotal() {
		return relatorioTotal;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public IRelatorioRepo getRelatorioRepo() {
		return relatorioRepo;
	}

	public void setRelatorioRepo(IRelatorioRepo relatorioRepo) {
		this.relatorioRepo = relatorioRepo;
	}

}