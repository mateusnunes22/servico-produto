package br.com.dynamic.repo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;

import org.springframework.stereotype.Repository;

import br.com.dynamic.entidade.Cliente;
import br.com.dynamic.entidade.Funcionario;
import br.com.dynamic.repo.interfaces.IClienteRepo;

@Repository
public class ClienteRepo extends JpaRepository<Cliente, Integer> implements IClienteRepo {
	
	private static final long serialVersionUID = 1L;

	public Integer getInsertedId() {
		return getEntityManager().createQuery("select max(c.id) from Cliente c", Integer.class).getSingleResult();
	}

	@Override
	public Cliente findByNome(String nome) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();		
		CriteriaQuery<Cliente> q = cb.createQuery(Cliente.class);
		Root<Cliente> cli = q.from(Cliente.class);

		q.where(cb.like(cli.<String>get("nome"), nome));
		q.orderBy(cb.asc(cli.get("nome")));

		try {
			return getEntityManager().createQuery(q.select(cli)).getSingleResult();
		} catch(NoResultException nre) {
			return null;
		}
	}

	@Override
	public List<Cliente> findAllByNome(String nome) {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Cliente> query = builder.createQuery(Cliente.class);
        Root<Cliente> root = query.from(Cliente.class);
        EntityType<Cliente> type = getEntityManager().getMetamodel().entity(Cliente.class);
        
        query.where(
			builder.like(
					builder.lower(root.get(type.getDeclaredSingularAttribute("nome", String.class))), 
					"%" + nome.toLowerCase() + "%"
			)
        );

	    return getEntityManager().createQuery(query).getResultList();
	}
	
	@Override
	public List<Cliente> listarAniversariantes(int numeroDias) {
		List<Cliente> aniversariantes = new ArrayList<Cliente>();
		Calendar cal2 = Calendar.getInstance();
        cal2.setTime(new Date());
        cal2.add(Calendar.DATE, numeroDias);
        
        Calendar hoje = Calendar.getInstance();   
        hoje.add(Calendar.DATE, -1);
        
		TypedQuery<Cliente> query = getEntityManager().createQuery("from Cliente c where nascimento is not null order by nascimento asc, nome asc", Cliente.class);
		List<Cliente> clientes = query.getResultList();
		
		for (Cliente c : clientes) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(c.getNascimento());
			cal.set(Calendar.YEAR, hoje.get(Calendar.YEAR));
			
			if (cal.after(hoje) && cal.before(cal2)){
				aniversariantes.add(c);
			}
		}
		
		return aniversariantes;
	}
	
	@Override
	public List<Cliente> listarTodosIndicadosPorFuncionario(Funcionario funcionario) {
		TypedQuery<Cliente> clientes = getEntityManager().createQuery(
				"FROM Cliente c WHERE c.funcionario = :funcionario OR (c.funcionario IS NOT NULL AND :funcionario IS NULL) ORDER BY c.nome", 
				Cliente.class
		);
		clientes.setParameter("funcionario", funcionario);
		
		return clientes.getResultList();
	}
	
	@Override
	public List<Cliente> listarTodosIndicadosPorCliente(Cliente cliente) {
		TypedQuery<Cliente> clientes = getEntityManager().createQuery(
				"FROM Cliente c WHERE c.cliente = :cliente OR (c.cliente IS NOT NULL AND :cliente IS NULL) ORDER BY c.nome", 
				Cliente.class
		);
		clientes.setParameter("cliente", cliente);
		
		return clientes.getResultList();
	}

}