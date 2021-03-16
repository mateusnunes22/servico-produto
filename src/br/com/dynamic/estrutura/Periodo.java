package br.com.dynamic.estrutura;

import java.io.Serializable;
import java.util.Date;

public class Periodo implements Serializable {

	private Date dataInicial;
	private Date dataFinal;
	
	public Periodo() {

	}

	public Periodo(Date dataInicial, Date dataFinal) {
		this.setDataInicial(dataInicial);
		this.setDataFinal(dataFinal);
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public Date getDataFinal() {
		return dataFinal;
	}
	
}