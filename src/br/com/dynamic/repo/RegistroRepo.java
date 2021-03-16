package br.com.dynamic.repo;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.dynamic.entidade.Auditoria;
import br.com.dynamic.entidade.Cliente;
import br.com.dynamic.entidade.Estoque;
import br.com.dynamic.entidade.Funcionario;
import br.com.dynamic.entidade.Produto;
import br.com.dynamic.entidade.Registro;
import br.com.dynamic.entidade.RegistroId;
import br.com.dynamic.entidade.Servico;
import br.com.dynamic.entidade.Usuario;
import br.com.dynamic.logica.DataLogica;
import br.com.dynamic.repo.interfaces.IAuditoriaRepo;
import br.com.dynamic.repo.interfaces.IClienteRepo;
import br.com.dynamic.repo.interfaces.IEstoqueRepo;
import br.com.dynamic.repo.interfaces.IRegistroRepo;

@Repository
public class RegistroRepo extends JpaRepository<Registro, Integer> implements IRegistroRepo {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private IEstoqueRepo estoqueRepo;
	
	@Autowired
	private IClienteRepo clienteRepo;
	
	@Autowired
	private IAuditoriaRepo auditoriaRepo;
	
	@Override
	public Registro getById(Integer id) {
		TypedQuery<Registro> q = getEntityManager().createQuery("select r from Registro r where r.id.idVenda = :venda", Registro.class);
		q.setParameter("venda", id);
		return q.getSingleResult();
	}
	
	@Override
	public Integer getInsertedId() {
		return getEntityManager().createQuery("select max(r.id.idVenda) from Registro r", Integer.class).getSingleResult();
	}
	
	@Override
	public List<Registro> findByCliente(Cliente cliente) {
		TypedQuery<Registro> q = getEntityManager().createQuery("from Registro u left join fetch u.cliente left join fetch u.servico where u.cliente.id = :id", Registro.class);
		q.setParameter("id", cliente.getId());
		
		return q.getResultList();
	}

	@Override
	public Registro BuscarVenda(Registro venda) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();		
		CriteriaQuery<Registro> q = cb.createQuery(Registro.class);
		Root<Registro> root = q.from(Registro.class);

		predicates.add(cb.equal(root.<String>get("valor"), venda.getValor()));
		predicates.add(cb.equal(root.<String>get("data"), venda.getData()));
		predicates.add(cb.equal(root.<String>get("hora"), venda.getHora()));

		q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		return getEntityManager().createQuery(q.select(root)).getSingleResult();
	}

	@Override
	public Registro findByVenda(Registro registro) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();		
		CriteriaQuery<Registro> q = cb.createQuery(Registro.class);
		Root<Registro> root = q.from(Registro.class);

		q.where(cb.equal(root.<String>get("id"), registro.getId()));

		return getEntityManager().createQuery(q.select(root)).getSingleResult();
	}
	
	@Override
	public List<Registro> findAll(Date dataInicial, Date dataFinal) {
		TypedQuery<Registro> q = getEntityManager().createQuery(
				"from Registro u where u.venda = true AND u.data BETWEEN :dataInicial AND :dataFinal order by data, hora", Registro.class);
		q.setParameter("dataInicial", dataInicial);
		q.setParameter("dataFinal", dataFinal);
		
		return q.getResultList();
	}

	@Override
	public List<Registro> findAllByFuncionario(Funcionario funcionario) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();		
		CriteriaQuery<Registro> q = cb.createQuery(Registro.class);
		Root<Registro> root = q.from(Registro.class);

		q.where(cb.like(root.<String>get("nome"), funcionario.getNome()));
		q.orderBy(cb.asc(root.get("nome")));
		
		return getEntityManager().createQuery(q.select(root)).getResultList();
	}
	
	@Override
	public List<Registro> findAllByProduto(Produto produto, Date dataInicial, Date dataFinal) {
		TypedQuery<Registro> q = getEntityManager().createQuery(
				"from Registro u where u.produto = :produto AND u.venda = true AND u.data BETWEEN :dataInicial AND :dataFinal order by data, hora", Registro.class);
		q.setParameter("produto", produto);
		q.setParameter("dataInicial", dataInicial);
		q.setParameter("dataFinal", dataFinal);
		
		return q.getResultList();
	}
	
	@Override
	public List<Registro> findAllByServico(Servico servico, Date dataInicial, Date dataFinal) {
		TypedQuery<Registro> q = getEntityManager().createQuery(
				"from Registro u where u.servico = :servico AND u.data BETWEEN :dataInicial AND :dataFinal order by data, hora", Registro.class);
		q.setParameter("servico", servico);
		q.setParameter("dataInicial", dataInicial);
		q.setParameter("dataFinal", dataFinal);
		
		return q.getResultList();
	}
	
	@Override
	public List<Registro> findAllServicos(Date dataInicial, Date dataFinal) {
		TypedQuery<Registro> q = getEntityManager().createQuery(
				"from Registro u where u.data BETWEEN :dataInicial AND :dataFinal order by data, hora", Registro.class);
		q.setParameter("dataInicial", dataInicial);
		q.setParameter("dataFinal", dataFinal);
		
		return q.getResultList();
	}

	@Override
	public List<Registro> BuscarAgendamento(Date data) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();		
		CriteriaQuery<Registro> q = cb.createQuery(Registro.class);
		Root<Registro> root = q.from(Registro.class);

		Date dataFim = new Date(Long.parseLong("9999999999999"));
		
		predicates.add(cb.between(root.<Date>get("data"), data, dataFim));
		predicates.add(cb.equal(root.<String>get("agendamento"), true));
		predicates.add(cb.equal(root.<String>get("venda"), true));	
		
		q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		q.orderBy(cb.asc(root.get("data")), cb.asc(root.get("hora")));
		
		return getEntityManager().createQuery(q.select(root)).getResultList();
	}

	@Override
	@Transactional
	public Registro agendar(Registro registro) {
		HttpSession session2 = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		Usuario usu = (Usuario) session2.getAttribute("currentUser");

		RegistroId registroId = new RegistroId();
		registroId.setUsuarioIdUsuario(usu.getId());

		registro.setFormaPagamento("Dinheiro");
		registro.setDiaSemana(DataLogica.diaDaSemana(registro.getData()));
		registro.setUsuario(usu);
		registro.setAgendamento(true);
		registro.setVenda(true);
		registro.setId(registroId);

		getEntityManager().merge(registro);
		getEntityManager().flush();
		
		Auditoria audit = new Auditoria();
		audit.preencherAuditoria(Auditoria.ADICAO, "Agendamento", getInsertedId());
		auditoriaRepo.auditar(audit);
		
		return registro;
	}

	@Override
	@Transactional
	public Registro comprar(Registro registro) {
		HttpSession session2 = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

		Usuario usu = (Usuario) session2.getAttribute("currentUser");
		Cliente cliente = (Cliente) session2.getAttribute("clienteVenda");

		RegistroId registroId = new RegistroId();
		registroId.setUsuarioIdUsuario(usu.getId());

		registro.setFormaPagamento("Dinheiro");
		registro.setData(new Date());
		registro.setDiaSemana(DataLogica.diaDaSemana(registro.getData()));
		registro.setHora(new Time(new Date().getTime() - 10800000));
		registro.setUsuario(usu);
		registro.setCliente(cliente);
		registro.setAgendamento(false);
		registro.setVenda(false);
		registro.setId(registroId);

		getEntityManager().merge(registro);
		getEntityManager().flush();
		
		Auditoria audit = new Auditoria();
		audit.preencherAuditoria(Auditoria.ADICAO, "Compra", getInsertedId());
		auditoriaRepo.auditar(audit);

		Estoque estoque = estoqueRepo.findByProduto(registro.getProduto());
		estoque.setQuantidadeAtual(estoque.getQuantidadeAtual() + 1);
		estoque.setQuantidadeTotal(estoque.getQuantidadeTotal() + 1);
		estoqueRepo.update(estoque);

		return registro;
	}

	@Override
	@Transactional
	public Registro despesa(Registro registro) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		Usuario usu = (Usuario) session.getAttribute("currentUser");

		RegistroId registroId = new RegistroId();
		registroId.setUsuarioIdUsuario(usu.getId());

		registro.setFormaPagamento("Dinheiro");
		registro.setData(new Date());
		registro.setDiaSemana(DataLogica.diaDaSemana(registro.getData()));
		registro.setHora(new Time(new Date().getTime() - 10800000));
		registro.setUsuario(usu);
		registro.setAgendamento(true);
		registro.setVenda(false);
		registro.setId(registroId);

		getEntityManager().merge(registro);
		getEntityManager().flush();
		
		Auditoria audit = new Auditoria();
		audit.preencherAuditoria(Auditoria.ADICAO, "Despesa", getInsertedId());
		auditoriaRepo.auditar(audit);

		return registro;
	}

	@Override
	@Transactional
	public Registro vender(Registro registro) {
		HttpSession session2 = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

		Usuario usu = (Usuario) session2.getAttribute("currentUser");

		RegistroId registroId = new RegistroId();
		registroId.setUsuarioIdUsuario(usu.getId());

		registro.setDiaSemana(DataLogica.diaDaSemana(registro.getData()));
		registro.setUsuario(usu);
		registro.setAgendamento(false);
		registro.setVenda(true);
		registro.setId(registroId);

		getEntityManager().merge(registro);
		getEntityManager().flush();
		
		Auditoria audit = new Auditoria();
		audit.preencherAuditoria(Auditoria.ADICAO, "Venda", getInsertedId());
		auditoriaRepo.auditar(audit);

		if (registro.getProduto() != null) {
			Estoque estoque = estoqueRepo.findByProduto(registro.getProduto());
			estoque.setQuantidadeAtual(estoque.getQuantidadeAtual() - 1);
			estoqueRepo.update(estoque);
		}
		
		return registro;
	}

	@Override
	@Transactional
	public Registro venderRetroativo(Registro registro, int idCliente, Date data) {
		HttpSession session2 = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

		Usuario usu = (Usuario) session2.getAttribute("currentUser");

		Cliente cliente = clienteRepo.getById(idCliente);

		RegistroId registroId = new RegistroId();
		registroId.setUsuarioIdUsuario(usu.getId());

		registro.setServico(registro.getServico());
		registro.setFuncionario(registro.getFuncionario());
		registro.setData(data);
		registro.setDiaSemana(DataLogica.diaDaSemana(registro.getData()));
		registro.setUsuario(usu);
		registro.setCliente(cliente);
		registro.setAgendamento(false);
		registro.setVenda(true);
		registro.setId(registroId);

		super.add(registro);

		// TODO é obrigatório remover do estoque mesmo que não haja registro
		if (registro.getProduto() != null) {
			Estoque estoque = estoqueRepo.findByProduto(registro.getProduto());
			estoque.setQuantidadeAtual(estoque.getQuantidadeAtual() - 1);
			estoqueRepo.update(estoque);
		}

		return registro;
	}

	@Override
	@Transactional
	public void removerVendaServico(Registro registro) {
		if (!getEntityManager().contains(registro)) {
			registro = getEntityManager().merge(registro);
		}
		getEntityManager().remove(registro);
		
		if (registro.getProduto() != null) {
			Estoque estoque = estoqueRepo.findByProduto(registro.getProduto());
			estoque.setQuantidadeAtual(estoque.getQuantidadeAtual() + 1);
			estoqueRepo.update(estoque);
		}

		Auditoria audit = new Auditoria();
		audit.preencherAuditoria(Auditoria.REMOCAO, getType().getSimpleName(), registro.getId().getIdVenda());

		auditoriaRepo.auditar(audit);
	}

	@Override
	public List<Registro> findAllByVenda(Integer id) {
		TypedQuery<Registro> query = getEntityManager().createQuery("from Registro u where u.id.idVenda = :idVenda", Registro.class);
		query.setParameter("idVenda", id);
		
		return query.getResultList();
	}

}