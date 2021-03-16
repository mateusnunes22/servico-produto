package br.com.dynamic.login;

public class Pagina {
	
	private String admistrador[];
	private String funcionario[];
	private String semAcesso[] = {"/login.jsf"};
	
	public Pagina()	{
		
		String pagina[] = {"/vendaRelatorio.jsf","/vendaAtrasada.jsf","/venda.jsf",
				"/servicoList.jsf","/servicoEdit.jsf","/servico.jsf","/rendimentoRelatorio.jsf",
				"/produtoList.jsf","/produtoEdit.jsf","/produto.jsf","/funcionarioRelatorio.jsf",
				"/fornecedorList.jsf","/fornecedor.jsf","/estoqueList.jsf","/despesa.jsf",
				"/compraRelatorio.jsf","/compra.jsf","/clienteRelatorio.jsf","/clienteList.jsf",
				"/categoriaList.jsf","/categoria.jsf","/agendamentoRelatorio.jsf",
				"/agendamento.jsf"	,"/login.jsf","/cliente.jsf","/home.jsf",
				"/clienteList.jsf","/funcionario.jsf","/funcionarioList.jsf",
				"/cadastro.jsf","/relatorio.jsf","/vendaPorCategoriaRelatorio.jsf",
				"/vendaPorProdutoRelatorio.jsf","/clienteDetalhadoRelatorio.jsf",
				"/auditoriaRelatorio.jsf","/cadastroUsuario.jsf"};
		
		funcionario = pagina;
		admistrador =  pagina;
		
	}

	public String[] getAdmistrador() {
		return admistrador;
	}

	public void setAdmistrador(String[] admistrador) {
		this.admistrador = admistrador;
	}

	public String[] getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(String[] funcionario) {
		this.funcionario = funcionario;
	}

	public String[] getSemAcesso() {
		return semAcesso;
	}

	public void setSemAcesso(String[] semAcesso) {
		this.semAcesso = semAcesso;
	}

}