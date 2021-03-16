package br.com.dynamic.relatorio;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.com.dynamic.entidade.Auditoria;
import br.com.dynamic.estrutura.Bean;
import br.com.dynamic.logica.IPagamentoLogica;
import br.com.dynamic.repo.interfaces.IRelatorioRepo;

@ManagedBean
@SessionScoped
public class AuditoriaBeanRpt {
	
	@ManagedProperty(value="#{relatorioRepo}")
	private IRelatorioRepo relatorioRepo;
	
	@ManagedProperty(value="#{pagamentoLogica}")
	private IPagamentoLogica pagamentoLogica;
	
	private Bean rpt = new Bean();
	public List<Auditoria> relatorio =  new ArrayList<Auditoria>();
	
	public String atualizar() {
		relatorio = relatorioRepo.relatorioAuditoria(rpt.getData().getDataInicial(), rpt.getData().getDataFinal());
		rpt.setSize(relatorio.size());
		custoUtilizacao();
		return null;
	}
	
	public List<Auditoria> getRelatorio() {
		return relatorio;
	}
	
	public void custoUtilizacao() {
		rpt.setTexto("Custo Variável: R$ " + pagamentoLogica.pagamento(relatorio.size()));
	}

	public void setRelatorio(List<Auditoria> relatorio) {
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

	public IPagamentoLogica getPagamentoLogica() {
		return pagamentoLogica;
	}

	public void setPagamentoLogica(IPagamentoLogica pagamentoLogica) {
		this.pagamentoLogica = pagamentoLogica;
	}

}