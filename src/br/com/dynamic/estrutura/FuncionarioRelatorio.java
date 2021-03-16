package br.com.dynamic.estrutura;

public class FuncionarioRelatorio{

	private String nome;
	private int numero;
	private float valorProduto;
	private float valorServico;
	private float valorTotal;
	private float valorPagamento;
	private boolean funcionario;
	
	public FuncionarioRelatorio() {
	}
	
	public FuncionarioRelatorio(String nome, int numero, float valorProduto,
			float valorServico, float valorTotal, float valorPagamento) {
		this.nome = nome;
		this.numero = numero;
		this.valorProduto = valorProduto;
		this.valorServico = valorServico;
		this.valorTotal = valorTotal;
		this.valorPagamento = valorPagamento;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public float getValorProduto() {
		return valorProduto;
	}

	public void setValorProduto(float valorProduto) {
		this.valorProduto = valorProduto;
	}

	public float getValorServico() {
		return valorServico;
	}

	public void setValorServico(float valorServico) {
		this.valorServico = valorServico;
	}

	public float getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(float valorTotal) {
		this.valorTotal = valorTotal;
	}

	public void setValorPagamento(float valorPagamento) {
		this.valorPagamento = valorPagamento;
	}

	public float getValorPagamento() {
		return valorPagamento;
	}

	public boolean isFuncionario() {
		return funcionario;
	}

	public void setFuncionario(boolean funcionario) {
		this.funcionario = funcionario;
	}
	
}