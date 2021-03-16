package br.com.dynamic.services;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dynamic.entidade.Cliente;
import br.com.dynamic.estrutura.ClienteRelatorio;
import br.com.dynamic.estrutura.Periodo;
import br.com.dynamic.repo.interfaces.IRelatorioRepo;

@Service
public class RelatorioService implements IRelatorioService {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private IRelatorioRepo relatorioRepo;
	
	public List<ClienteRelatorio> totalizarComprasClientes(List<Cliente> clientes, Periodo periodo) {
		List<ClienteRelatorio> relatorio = relatorioRepo.totalizarComprasClientes(clientes, periodo);
		
		Collections.sort(relatorio, new Comparator<ClienteRelatorio>() {
			@Override
			public int compare(ClienteRelatorio o1, ClienteRelatorio o2) {
				if (o1.getValorTotal() > o2.getValorTotal()) {
					return -1;
				} else if (o1.getValorTotal() < o2.getValorTotal()) {
					return 1;
				}
				return 0;
			}
		});
		
		if (clientes.size() > 1) {
			float valorProduto = 0, valorServico = 0;
			ClienteRelatorio total = new ClienteRelatorio();
			total.setNome("Todos os clientes");
			
			for (ClienteRelatorio cr : relatorio) {
				valorProduto += cr.getValorProduto();
				valorServico += cr.getValorServico();
			}
			
			total.setValorProduto(valorProduto);
			total.setValorServico(valorServico);
			total.setValorTotal(valorProduto + valorServico);
			
			relatorio.add(0, total);
		}
		
		return relatorio;
	}
	
}