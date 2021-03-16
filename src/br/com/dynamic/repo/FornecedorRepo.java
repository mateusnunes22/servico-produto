package br.com.dynamic.repo;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;

import org.springframework.stereotype.Repository;

import br.com.dynamic.entidade.Fornecedor;
import br.com.dynamic.repo.interfaces.IFornecedorRepo;

@Repository
public class FornecedorRepo extends JpaRepository<Fornecedor, Integer> implements IFornecedorRepo {
	
	private static final long serialVersionUID = 1L;

	@Override
	public Integer getInsertedId() {
		return getEntityManager().createQuery("select max(f.id) from Fornecedor f", Integer.class).getSingleResult();
	}

	@Override
	public List<Fornecedor> findAllByNome(String nome) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();		
		CriteriaQuery<Fornecedor> q = cb.createQuery(Fornecedor.class);
		Root<Fornecedor> root = q.from(Fornecedor.class);
		EntityType<Fornecedor> type = getEntityManager().getMetamodel().entity(Fornecedor.class);
        
        q.where(
			cb.like(
					cb.lower(root.get(type.getDeclaredSingularAttribute("nome", String.class))), 
					"%" + nome.toLowerCase() + "%"
			)
        );
		q.orderBy(cb.asc(root.get("nome")));

		return getEntityManager().createQuery(q.select(root)).getResultList();
	}

	@Override
	public Fornecedor findByNome(String nome){
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();		
		CriteriaQuery<Fornecedor> q = cb.createQuery(Fornecedor.class);
		Root<Fornecedor> root = q.from(Fornecedor.class);

		q.where(cb.equal(root.<String>get("nome"), nome));
	
		try {
			return getEntityManager().createQuery(q.select(root)).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}