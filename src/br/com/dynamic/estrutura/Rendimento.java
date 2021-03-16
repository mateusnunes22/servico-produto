package br.com.dynamic.estrutura;

public class Rendimento{

	private Float faturamento;
	private Float despesa;
	private Float compra;
	private Float lucro;
	
	public Rendimento() {

	}

	public Rendimento(Float faturamento, Float despesa, Float compra, Float lucro) {
		this.faturamento = faturamento;
		this.despesa = despesa;
		this.compra = compra;
		this.lucro = lucro;
	}

	public Float getFaturamento() {
		return faturamento;
	}

	public void setFaturamento(Float faturamento) {
		this.faturamento = faturamento;
	}

	public Float getDespesa() {
		return despesa;
	}

	public void setDespesa(Float despesa) {
		this.despesa = despesa;
	}

	public Float getCompra() {
		return compra;
	}

	public void setCompra(Float compra) {
		this.compra = compra;
	}

	public Float getLucro() {
		return lucro;
	}

	public void setLucro(Float lucro) {
		this.lucro = lucro;
	}
	
}
