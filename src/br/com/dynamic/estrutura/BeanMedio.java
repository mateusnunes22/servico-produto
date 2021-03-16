package br.com.dynamic.estrutura;

import java.io.Serializable;

public class BeanMedio implements Serializable {

	private Periodo data;
	private Periodo data2;
	private String pagina;
	private String texto;
	private String texto2;
	private String texto3;
	private int numero;
	private int numero2;
	private int numero3;
	private int size;
	private int size2;
	private int size3;
	private float valor;
	private float valor2;
	private float valor3;
	
	
	public BeanMedio() {
		
		this.data = new Periodo();
		this.data2 = new Periodo();
		
	}
	
	public BeanMedio(Periodo data,String pagina) {
		this.data = data;
		this.pagina = pagina;
	}

	public BeanMedio(Periodo data, Periodo data2, String pagina, String texto, String texto2,
			String texto3, int numero, int numero2, int numero3, int size,
			int size2, int size3, float valor, float valor2, float valor3) {
		this.data = data;
		this.data2 = data2;
		this.pagina = pagina;
		this.texto = texto;
		this.texto2 = texto2;
		this.texto3 = texto3;
		this.numero = numero;
		this.numero2 = numero2;
		this.numero3 = numero3;
		this.size = size;
		this.size2 = size2;
		this.size3 = size3;
		this.valor = valor;
		this.valor2 = valor2;
		this.valor3 = valor3;
	}

	public Periodo getData() {
		return data;
	}

	public void setData(Periodo data) {
		this.data = data;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public void setData2(Periodo data2) {
		this.data2 = data2;
	}

	public Periodo getData2() {
		return data2;
	}

	public void setTexto2(String texto2) {
		this.texto2 = texto2;
	}

	public String getTexto2() {
		return texto2;
	}

	public void setTexto3(String texto3) {
		this.texto3 = texto3;
	}

	public String getTexto3() {
		return texto3;
	}

	public void setNumero2(int numero2) {
		this.numero2 = numero2;
	}

	public int getNumero2() {
		return numero2;
	}

	public void setNumero3(int numero3) {
		this.numero3 = numero3;
	}

	public int getNumero3() {
		return numero3;
	}

	public void setSize2(int size2) {
		this.size2 = size2;
	}

	public int getSize2() {
		return size2;
	}

	public void setSize3(int size3) {
		this.size3 = size3;
	}

	public int getSize3() {
		return size3;
	}

	public void setValor2(float valor2) {
		this.valor2 = valor2;
	}

	public float getValor2() {
		return valor2;
	}

	public void setValor3(float valor3) {
		this.valor3 = valor3;
	}

	public float getValor3() {
		return valor3;
	}

	public void setPagina(String pagina) {
		this.pagina = pagina;
	}

	public String getPagina() {
		return pagina;
	}
	
}
