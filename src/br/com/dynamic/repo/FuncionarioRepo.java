package br.com.dynamic.repo;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;

import org.springframework.stereotype.Repository;

import br.com.dynamic.entidade.Funcionario;
import br.com.dynamic.repo.interfaces.IFuncionarioRepo;

@Repository
public class FuncionarioRepo extends JpaRepository<Funcionario, Integer> implements IFuncionarioRepo {
	
	@Override
	public Integer getInsertedId() {
		return getEntityManager().createQuery("select max(f.id) from Funcionario f", Integer.class).getSingleResult();
	}
	
	@Override
	public List<Funcionario> findAllByNome(String nome){
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();		
		CriteriaQuery<Funcionario> q = cb.createQuery(Funcionario.class);
		Root<Funcionario> root = q.from(Funcionario.class);
		EntityType<Funcionario> type = getEntityManager().getMetamodel().entity(Funcionario.class);

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
	public Funcionario findByNome(String nome){
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();		
		CriteriaQuery<Funcionario> q = cb.createQuery(Funcionario.class);
		Root<Funcionario> root = q.from(Funcionario.class);

		q.where(cb.like(root.<String>get("nome"), nome));
		
		try {
			return getEntityManager().createQuery(q.select(root)).getSingleResult();
		} catch(NoResultException e) {
			return null;
		}
	}

}