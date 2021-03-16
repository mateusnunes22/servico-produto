package br.com.dynamic.repo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
import br.com.dynamic.repo.interfaces.IClienteRepo;
import br.com.dynamic.repo.interfaces.IFuncionarioRepo;
import br.com.dynamic.repo.interfaces.IRelatorioRepo;

@Repository
public class RelatorioRepo implements IRelatorioRepo {

	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private IClienteRepo clienteRepo;
	
	@Autowired
	private IFuncionarioRepo funcionarioRepo;

	@Override
	public List<Registro> relatorioAgendamento(Date date) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Registro> q = cb.createQuery(Registro.class);
		Root<Registro> root = q.from(Registro.class);

		Date dataInicio = new Date();
		Date dataFim = new Date(Long.parseLong("9999999999999"));

		predicates.add(cb.between(root.<Date> get("data"), dataInicio, dataFim));
		predicates.add(cb.equal(root.<Boolean> get("agendamento"), true));
		predicates.add(cb.equal(root.<Boolean> get("venda"), true));

		q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		q.orderBy(cb.asc(root.get("data")), cb.asc(root.get("hora")));

		return em.createQuery(q.select(root)).getResultList();
	}
	
	@Override
	public List<Registro> relatorioAgendamento(Date data, Funcionario funcionario) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Registro> q = cb.createQuery(Registro.class);
		Root<Registro> root = q.from(Registro.class);

		Date dataFim = new Date(Long.parseLong("9999999999999"));

		if (funcionario != null) {
			predicates.add(cb.equal(root.<Funcionario> get("funcionario"), funcionario));
		}

		predicates.add(cb.between(root.<Date> get("data"), data, dataFim));
		predicates.add(cb.equal(root.<Boolean> get("agendamento"), true));
		predicates.add(cb.equal(root.<Boolean> get("venda"), true));

		q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		q.orderBy(cb.asc(root.get("data")), cb.asc(root.get("hora")));

		return em.createQuery(q.select(root)).getResultList();
	}

	@Override
	public List<ClienteRelatorio> totalizarComprasClientes(List<Cliente> clientes, Periodo periodo) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Float> queryRegistro = cb.createQuery(Float.class);
		Root<Registro> registroRoot = queryRegistro.from(Registro.class);

		ClienteRelatorio clienteRelatorio;
		float valorSomatoria;
		List<ClienteRelatorio> listaFinal = new ArrayList<ClienteRelatorio>();
		
		for (Cliente cliente : clientes) {
			clienteRelatorio = new ClienteRelatorio();
			clienteRelatorio.setNome(cliente.getNome());
			clienteRelatorio.setCliente(true);
			
			predicates.clear();
			queryRegistro = cb.createQuery(Float.class);
			registroRoot = queryRegistro.from(Registro.class);
			
			if (periodo.getDataInicial() != null && periodo.getDataFinal() != null) {
				predicates.add(cb.between(registroRoot.<Date> get("data"), periodo.getDataInicial(), periodo.getDataFinal()));
			}

			predicates.add(cb.equal(registroRoot.<Boolean> get("agendamento"), false));
			predicates.add(cb.equal(registroRoot.<Boolean> get("venda"), true));
			predicates.add(cb.isNotNull(registroRoot.<Produto> get("produto")));
			predicates.add(cb.equal(registroRoot.<Cliente> get("cliente"), cliente));
			
			queryRegistro.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

			try {
				valorSomatoria = em.createQuery(queryRegistro.select(cb.sum(registroRoot.<Float>get("valor")))).getSingleResult();
				clienteRelatorio.setValorProduto(valorSomatoria);
			} catch (Exception e) {
				clienteRelatorio.setValorProduto(0);
			}

			predicates.clear();
			queryRegistro = cb.createQuery(Float.class);
			registroRoot = queryRegistro.from(Registro.class);

			if (periodo.getDataInicial() != null && periodo.getDataFinal() != null) {
				predicates.add(cb.between(registroRoot.<Date> get("data"), periodo.getDataInicial(), periodo.getDataFinal()));
			}

			predicates.add(cb.equal(registroRoot.<Boolean> get("agendamento"), false));
			predicates.add(cb.equal(registroRoot.<Boolean> get("venda"), true));
			predicates.add(cb.isNotNull(registroRoot.<Servico> get("servico")));
			predicates.add(cb.equal(registroRoot.<Cliente> get("cliente"), cliente));
			
			queryRegistro.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

			try {
				valorSomatoria = em.createQuery(queryRegistro.select(cb.sum(registroRoot.<Float>get("valor")))).getSingleResult();
				clienteRelatorio.setValorServico(valorSomatoria);
			} catch (Exception e) {
				clienteRelatorio.setValorServico(0);
			}

			clienteRelatorio.setValorTotal(clienteRelatorio.getValorProduto() + clienteRelatorio.getValorServico());
			listaFinal.add(clienteRelatorio);
		}
		
		return listaFinal;
	}

	@Override
	public List<Registro> relatorioClienteDetalhado(Bean rpt) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Registro> q = cb.createQuery(Registro.class);
		Root<Registro> root = q.from(Registro.class);

		Cliente cliente = clienteRepo.findByNome(rpt.getTexto());

		if (rpt.getData().getDataInicial() != null && rpt.getData().getDataFinal() != null) {
			predicates.add(cb.between(root.<Date> get("data"), rpt.getData().getDataInicial(), rpt.getData().getDataFinal()));
		}
		
		predicates.add(cb.equal(root.<Boolean> get("agendamento"), false));
		predicates.add(cb.equal(root.<Boolean> get("venda"), true));
		predicates.add(cb.equal(root.<Cliente> get("cliente"), cliente));

		q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		q.orderBy(cb.asc(root.get("data")), cb.asc(root.get("hora")));

		return em.createQuery(q.select(root)).getResultList();
	}

	@Override
	public List<Registro> relatorioFuncionarioDetalhado(BeanMedio rpt) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Registro> q = cb.createQuery(Registro.class);
		Root<Registro> root = q.from(Registro.class);

		Funcionario funcionario = funcionarioRepo.findByNome(rpt.getTexto());

		if (rpt.getData().getDataInicial() != null && rpt.getData().getDataFinal() != null) {
			predicates.add(cb.between(root.<Date> get("data"), rpt.getData().getDataInicial(), rpt.getData().getDataFinal()));
		}
		
		predicates.add(cb.equal(root.<Boolean> get("agendamento"), false));
		predicates.add(cb.equal(root.<Boolean> get("venda"), true));
		predicates.add(cb.equal(root.<Cliente> get("funcionario"), funcionario));

		q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		q.orderBy(cb.asc(root.get("data")), cb.asc(root.get("hora")));

		return em.createQuery(q.select(root)).getResultList();
	}
	
	@Override
	public List<Registro> relatorioCompraDetalhado(Date dataInicial, Date dataFinal) {
		TypedQuery<Registro> q = em.createQuery(
				"from Registro u where u.data BETWEEN :dataInicial AND :dataFinal AND agendamento = false AND venda = false order by data, hora", Registro.class);
		q.setParameter("dataInicial", dataInicial);
		q.setParameter("dataFinal", dataFinal);
		
		return q.getResultList();
	}
	
	@Override
	public List<Registro> relatorioCompraDetalhado(Produto produto, Date dataInicial, Date dataFinal) {
		TypedQuery<Registro> q = em.createQuery(
				"from Registro u where u.produto = :produto AND u.data BETWEEN :dataInicial AND :dataFinal AND agendamento = false AND venda = false order by data, hora", Registro.class);
		q.setParameter("produto", produto);
		q.setParameter("dataInicial", dataInicial);
		q.setParameter("dataFinal", dataFinal);
		
		return q.getResultList();
	}

	@Override
	public List<Produto> relatorioCompraTotal(Date dataInicial, Date dataFinal, Produto produto) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Produto> q = cb.createQuery(Produto.class);
		CriteriaQuery<Float> queryRegistro = cb.createQuery(Float.class);
		Root<Produto> root = q.from(Produto.class);
		Root<Registro> registroRoot = queryRegistro.from(Registro.class);

		Produto total = new Produto();
		List<Produto> listaFinal = new ArrayList<Produto>();
		
		if (produto == null) {
			total.setNome("Todos os produtos");
			total.setValorVenda(0f);
			listaFinal.add(total);
		} else {
			q.where(cb.equal(root.<String> get("id"), produto.getId()));
		}
		
		List<Produto> listaProduto = em.createQuery(q.select(root)).getResultList();
		Float valorTotal = new Float(0);

		for (Produto p : listaProduto) {
			predicates.clear();

			if (dataInicial != null && dataFinal != null) {
				predicates.add(cb.between(registroRoot.<Date> get("data"), dataInicial, dataFinal));
			}
			
			predicates.add(cb.equal(registroRoot.<Boolean> get("agendamento"), false));
			predicates.add(cb.equal(registroRoot.<Boolean> get("venda"), false));
			predicates.add(cb.equal(registroRoot.<Produto> get("produto"), p));
			
			queryRegistro.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			Float valor = em.createQuery(queryRegistro.select(cb.sum(registroRoot.<Float>get("valor")))).getSingleResult();

			try {
				p.setValorVenda(valor);
				valorTotal += valor;
			} catch (Exception e) {
				p.setValorVenda(0f);
			}

			listaFinal.add(p);
		}

		if (produto == null) {
			total.setValorVenda(valorTotal);
		}

		return listaFinal;
	}

	@Override
	public List<FuncionarioRelatorio> relatorioFuncionarioTotal(BeanMedio funcionarioParam) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Funcionario> funcionarioQuery = cb.createQuery(Funcionario.class);
		CriteriaQuery<Float> queryRegistro = null;
		Root<Funcionario> root = funcionarioQuery.from(Funcionario.class);
		Root<Registro> registroRoot = null;
		
		List<FuncionarioRelatorio> listaFinal = new ArrayList<FuncionarioRelatorio>();
		FuncionarioRelatorio funcionarioRelatorio, total = new FuncionarioRelatorio();
		Float valorSomatoria, valorTotal= new Float(0), valorProduto= new Float(0), valorServico= new Float(0);
		
		funcionarioQuery.orderBy(cb.asc(root.get("nome")));
		
		List<Funcionario> listaFuncionario;
		
		if (funcionarioParam.getNumero() == 0) {
			 total.setNome("Todos os funcionarios");
			 total.setValorProduto(0);
			 total.setValorServico(0);
			 total.setValorTotal(0);
			 
			 listaFinal.add(total);
		} else {
			 funcionarioQuery.where(cb.equal(root.<String> get("id"), funcionarioParam.getNumero()));
		}
		
		listaFuncionario = em.createQuery(funcionarioQuery.select(root)).getResultList();
		
		for (Funcionario funcionario : listaFuncionario) {
			funcionarioRelatorio = new FuncionarioRelatorio();
			funcionarioRelatorio.setNome(funcionario.getNome());
			funcionarioRelatorio.setFuncionario(true);
			
			predicates.clear();
			queryRegistro = cb.createQuery(Float.class);
			registroRoot = queryRegistro.from(Registro.class);
			
			if (funcionarioParam.getData().getDataInicial() != null && funcionarioParam.getData().getDataFinal() != null) {
				predicates.add(cb.between(registroRoot.<Date> get("data"), funcionarioParam.getData().getDataInicial(), funcionarioParam.getData().getDataFinal()));
			}

			predicates.add(cb.equal(registroRoot.<Boolean> get("agendamento"), false));
			predicates.add(cb.equal(registroRoot.<Boolean> get("venda"), true));
			predicates.add(cb.isNotNull(registroRoot.<String> get("produto")));
			predicates.add(cb.equal(registroRoot.<Funcionario> get("funcionario"), funcionario));
			
			queryRegistro.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			valorSomatoria = em.createQuery(queryRegistro.select(cb.sum(registroRoot.<Float>get("valor")))).getSingleResult();

			try {
				funcionarioRelatorio.setValorProduto(valorSomatoria);
				valorProduto += valorSomatoria;
			} catch (Exception e) {
				funcionarioRelatorio.setValorProduto(0);
			}

			predicates.clear();
			queryRegistro = cb.createQuery(Float.class);
			registroRoot = queryRegistro.from(Registro.class);
			
			if (funcionarioParam.getData().getDataInicial() != null && funcionarioParam.getData().getDataFinal() != null) {
				predicates.add(cb.between(registroRoot.<Date> get("data"), funcionarioParam.getData().getDataInicial(), funcionarioParam.getData().getDataFinal()));
			}

			predicates.add(cb.equal(registroRoot.<String> get("agendamento"), false));
			predicates.add(cb.equal(registroRoot.<String> get("venda"), true));
			predicates.add(cb.isNotNull(registroRoot.<String> get("servico")));
			predicates.add(cb.equal(registroRoot.<Funcionario> get("funcionario"), funcionario));
			
			queryRegistro.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			valorSomatoria = em.createQuery(queryRegistro.select(cb.sum(registroRoot.<Float>get("valor")))).getSingleResult();

			try {
				funcionarioRelatorio.setValorServico(valorSomatoria);
				valorServico += valorSomatoria;
			} catch (Exception e) {
				funcionarioRelatorio.setValorServico(0);
			}

			funcionarioRelatorio.setValorTotal(funcionarioRelatorio.getValorProduto() + funcionarioRelatorio.getValorServico());
			valorTotal += funcionarioRelatorio.getValorTotal();
			listaFinal.add(funcionarioRelatorio);
		}
		
				
		if (funcionarioParam.getNumero() == 0) {
			total.setValorTotal(valorTotal);
			total.setValorProduto(valorProduto);
			total.setValorServico(valorServico);

			listaFinal.set(0, total);
		}

		return listaFinal;
	}

	@Override
	public List<Servico> relatorioVendaServicoTotal(Date dataInicial, Date dataFinal, Servico servico) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Servico> servicoQuery = cb.createQuery(Servico.class);
		CriteriaQuery<Float> queryRegistro = cb.createQuery(Float.class);
		Root<Servico> root = servicoQuery.from(Servico.class);
		Root<Registro> registroRoot = queryRegistro.from(Registro.class);

		List<Servico> listaFinal = new ArrayList<Servico>();
		Servico total = new Servico();
		Float valorTotal = new Float(0);
		
		if (servico == null) {
			total.setNome("Todos os serviços");
			total.setValor(0);

			listaFinal.add(total);
		} else {
			servicoQuery.where(cb.equal(root.<String> get("id"), servico.getId()));
		}

		servicoQuery.orderBy(cb.asc(root.get("nome")));

		List<Servico> listaServico = em.createQuery(servicoQuery.select(root)).getResultList();

		for (Servico s : listaServico) {
			predicates.clear();

			if (dataInicial != null && dataFinal != null) {
				predicates.add(cb.between(registroRoot.<Date> get("data"), dataInicial, dataFinal));
			}

			predicates.add(cb.equal(registroRoot.<Boolean> get("agendamento"), false));
			predicates.add(cb.equal(registroRoot.<Boolean> get("venda"), true));
			predicates.add(cb.equal(registroRoot.<Integer> get("servico"), s));

			queryRegistro.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			Float valor =  em.createQuery(queryRegistro.select(cb.sum(registroRoot.<Float>get("valor")))).getSingleResult();

			try {
				s.setValor(valor);
				valorTotal += valor;
			} catch (Exception e) {
				s.setValor(0);
			}

			listaFinal.add(s);
		}

		if (servico == null) {
			total.setValor(valorTotal);
			listaFinal.set(0, total);
		}

		return listaFinal;
	}

	@Override
	public List<Produto> relatorioVendaProdutoTotal(Date dataInicial, Date dataFinal, Produto produto) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Produto> produtoQuery = cb.createQuery(Produto.class);
		CriteriaQuery<Float> queryRegistro = cb.createQuery(Float.class);
		Root<Produto> root = produtoQuery.from(Produto.class);
		Root<Registro> registroRoot = queryRegistro.from(Registro.class);

		List<Produto> listaFinal = new ArrayList<Produto>();
		Produto total = new Produto();
		Float valorTotal = new Float(0);
		
		if (produto == null) {
			total.setNome("Todos os produtos");
			total.setValorVenda(0f);
			listaFinal.add(total);
			
			produtoQuery.orderBy(cb.asc(root.get("nome")));
		} else {
			produtoQuery.where(cb.equal(root.<String> get("id"), produto.getId()));
		}

		List<Produto> listaProduto = em.createQuery(produtoQuery.select(root)).getResultList();

		for (Produto p : listaProduto) {
			predicates.clear();

			if (dataInicial != null && dataFinal != null) {
				predicates.add(cb.between(registroRoot.<Date> get("data"), dataInicial, dataFinal));
			}

			predicates.add(cb.equal(registroRoot.<Boolean> get("agendamento"), false));
			predicates.add(cb.equal(registroRoot.<Boolean> get("venda"), true));
			predicates.add(cb.equal(registroRoot.<Integer> get("produto"), p));

			queryRegistro.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			Float valor =  em.createQuery(queryRegistro.select(cb.sum(registroRoot.<Float>get("valor")))).getSingleResult();
			
			try {
				p.setValorVenda(valor);
				valorTotal += valor;
			} catch (Exception e) {
				p.setValorVenda(0f);
			}

			listaFinal.add(p);
		}

		if (produto == null) {
			total.setValorVenda(valorTotal);
			listaFinal.set(0, total);
		}

		return listaFinal;
	}

	@Override
	public List<Registro> relatorioVendaServico(Date dataInicial, Date dataFinal) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Registro> query = cb.createQuery(Registro.class);
		Root<Registro> root = query.from(Registro.class);
		
		if (dataInicial != null && dataFinal != null) {
			predicates.add(cb.between(root.<Date> get("data"), dataInicial, dataFinal));
		}

		predicates.add(cb.equal(root.<Boolean> get("agendamento"), false));
		predicates.add(cb.equal(root.<Boolean> get("venda"), true));
		predicates.add(cb.isNotNull(root.<Integer> get("servico")));
		
		query.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		query.orderBy(cb.asc(root.get("data")), cb.asc(root.get("hora")));

		return em.createQuery(query.select(root)).getResultList();
	}

	@Override
	public List<Registro> relatorioVendaProduto(Date dataInicial, Date dataFinal) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Registro> query = cb.createQuery(Registro.class);
		Root<Registro> root = query.from(Registro.class);
		
		if (dataInicial != null && dataFinal != null) {
			predicates.add(cb.between(root.<Date> get("data"), dataInicial, dataFinal));
		}

		predicates.add(cb.equal(root.<Boolean> get("agendamento"), false));
		predicates.add(cb.equal(root.<Boolean> get("venda"), true));
		predicates.add(cb.isNotNull(root.<Integer> get("produto")));
		
		query.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		query.orderBy(cb.asc(root.get("data")), cb.asc(root.get("hora")));

		return em.createQuery(query.select(root)).getResultList();
	}

	@Override
	public List<Rendimento> relatorioRendimento(Bean rpt) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Float> queryRegistro;
		Root<Registro> root;
		
		List<Rendimento> listaFinal = new ArrayList<Rendimento>();

		float valor;
		Rendimento total = new Rendimento();
		
		queryRegistro = cb.createQuery(Float.class);
		root = queryRegistro.from(Registro.class);
				
		if (rpt.getData().getDataInicial() != null && rpt.getData().getDataFinal() != null) {
			predicates.add(cb.between(root.<Date> get("data"), rpt.getData().getDataInicial(), rpt.getData().getDataFinal()));
		}

		predicates.add(cb.equal(root.<Boolean> get("agendamento"), false));
		predicates.add(cb.equal(root.<Boolean> get("venda"), true));

		try {
			queryRegistro.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			valor =  em.createQuery(queryRegistro.select(cb.sum(root.<Float>get("valor")))).getSingleResult();
			total.setFaturamento(valor);
		} catch (Exception e) {
			total.setFaturamento(new Float(0));
		}
		
		predicates.clear();
		queryRegistro = cb.createQuery(Float.class);
		root = queryRegistro.from(Registro.class);
		
		if (rpt.getData().getDataInicial() != null && rpt.getData().getDataFinal() != null) {
			predicates.add(cb.between(root.<Date> get("data"), rpt.getData().getDataInicial(), rpt.getData().getDataFinal()));
		}
		
		predicates.add(cb.equal(root.<Boolean> get("agendamento"), true));
		predicates.add(cb.equal(root.<Boolean> get("venda"), false));
		
		try {
			queryRegistro.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			valor =  em.createQuery(queryRegistro.select(cb.sum(root.<Float>get("valor")))).getSingleResult();
			total.setDespesa(valor);
		} catch (Exception e) {
			total.setDespesa(new Float(0));
		}
		
		predicates.clear();
		queryRegistro = cb.createQuery(Float.class);
		root = queryRegistro.from(Registro.class);

		if (rpt.getData().getDataInicial() != null && rpt.getData().getDataFinal() != null) {
			predicates.add(cb.between(root.<Date> get("data"), rpt.getData().getDataInicial(), rpt.getData().getDataFinal()));
		}

		predicates.add(cb.equal(root.<Boolean> get("agendamento"), false));
		predicates.add(cb.equal(root.<Boolean> get("venda"), false));

		try {
			queryRegistro.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			valor =  em.createQuery(queryRegistro.select(cb.sum(root.<Float>get("valor")))).getSingleResult();
			total.setCompra(valor);
		} catch (Exception e) {
			total.setCompra(new Float(0));
		}

		total.setLucro(total.getFaturamento() - total.getDespesa() - total.getCompra());
		listaFinal.add(total);

		return listaFinal;
	}

	@Override
	public List<Auditoria> relatorioAuditoria(Date dataInicial, Date dataFinal) {
		TypedQuery<Auditoria> query = em.createQuery("FROM Auditoria a WHERE a.data BETWEEN :dataInicial AND :dataFinal ORDER BY data desc, hora desc", Auditoria.class);
		query.setParameter("dataInicial", dataInicial);
		query.setParameter("dataFinal", dataFinal);
		
		return query.getResultList();
	}

	@Override
	public List<Registro> relatorioDespesaTotal(Date dataInicial, Date dataFinal) {
		TypedQuery<Registro> query = em.createQuery("FROM Registro r WHERE r.agendamento = true AND r.venda = false AND r.data BETWEEN :dataInicial AND :dataFinal ORDER BY data, hora", Registro.class);
		query.setParameter("dataInicial", dataInicial);
		query.setParameter("dataFinal", dataFinal);

		return query.getResultList();
	}

}