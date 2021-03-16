package br.com.dynamic.estrutura;

import java.io.Serializable;
import br.com.dynamic.entidade.Cliente;
import br.com.dynamic.entidade.Funcionario;

public class Bean implements Serializable {

	private Periodo data;
	private String texto;
	private String pagina;
	private int numero;
	private int size;
	private float valor;
	private Cliente cliente;
	private Funcionario funcionario;
	private Cliente clienteIndicacao;
	private Funcionario funcionarioIndicacao;

	public Bean() {
		this.data = new Periodo();
	}

	public Bean(Periodo data, String pagina) {
		this.data = data;
		this.pagina = pagina;
	}

	public Bean(Periodo data, String texto, String pagina, int numero,
			int size, float valor) {
		this.data = data;
		this.texto = texto;
		this.pagina = pagina;
		this.numero = numero;
		this.size = size;
		this.valor = valor;
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

	public void setPagina(String pagina) {
		this.pagina = pagina;
	}

	public String getPagina() {
		return pagina;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Cliente getClienteIndicacao() {
		return clienteIndicacao;
	}

	public void setClienteIndicacao(Cliente clienteIndicacao) {
		this.clienteIndicacao = clienteIndicacao;
	}

	public Funcionario getFuncionarioIndicacao() {
		return funcionarioIndicacao;
	}

	public void setFuncionarioIndicacao(Funcionario funcionarioIndicacao) {
		this.funcionarioIndicacao = funcionarioIndicacao;
	}

}