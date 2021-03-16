package br.com.dynamic.repo.interfaces;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import br.com.dynamic.entidade.Auditoria;
import br.com.dynamic.entidade.Cliente;
import br.com.dynamic.entidade.Funcionario;
import br.com.dynamic.entidade.Produto;
import br.com.dynamic.entidade.Registro;
import br.com.dynamic.entidade.Servico;
import br.com.dynamic.estrutura.Bean;
import br.com.dynamic.estrutura.BeanMedio;
import br.com.dynamic.estrutura.ClienteRelatorio;
import br.com.dynamic.estrutura.FuncionarioRelatorio;
import br.com.dynamic.estrutura.Periodo;
import br.com.dynamic.estrutura.Rendimento;

public interface IRelatorioRepo extends Serializable {
	
	public List<Registro> relatorioAgendamento(Date date);

	public List<Registro> relatorioAgendamento(Date data, Funcionario funcionario);

	public List<ClienteRelatorio> totalizarComprasClientes(List<Cliente> clientes, Periodo periodo);

	public List<Registro> relatorioClienteDetalhado(Bean rpt);

	public List<Registro> relatorioCompraDetalhado(Date dataInicial, Date dataFinal);
	
	public List<Produto> relatorioCompraTotal(Date dataInicial, Date dataFinal, Produto produto);

	public List<FuncionarioRelatorio> relatorioFuncionarioTotal(BeanMedio funcionarioParam);

	public List<Servico> relatorioVendaServicoTotal(Date dataInicial, Date dataFinal, Servico servico);

	public List<Produto> relatorioVendaProdutoTotal(Date dataInicial, Date dataFinal, Produto produto);

	public List<Registro> relatorioVendaServico(Date dataInicial, Date dataFinal);

	public List<Registro> relatorioVendaProduto(Date dataInicial, Date dataFinal);

	public List<Rendimento> relatorioRendimento(Bean rpt);

	public List<Registro> relatorioFuncionarioDetalhado(BeanMedio rpt);

	public List<Registro> relatorioCompraDetalhado(Produto produto, Date dataInicial, Date dataFinal);

	public List<Registro> relatorioDespesaTotal(Date dataInicial, Date dataFinal);

	public List<Auditoria> relatorioAuditoria(Date dataInicial, Date dataFinal);

}