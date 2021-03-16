package br.com.dynamic.services;

import java.io.Serializable;
import java.util.List;

import br.com.dynamic.entidade.Cliente;
import br.com.dynamic.estrutura.ClienteRelatorio;
import br.com.dynamic.estrutura.Periodo;

public interface IRelatorioService extends Serializable {

	public List<ClienteRelatorio> totalizarComprasClientes(List<Cliente> clientes, Periodo periodo);

}