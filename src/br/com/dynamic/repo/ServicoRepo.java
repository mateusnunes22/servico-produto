package br.com.dynamic.repo;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;

import org.springframework.stereotype.Repository;

import br.com.dynamic.entidade.Categoria;
import br.com.dynamic.entidade.Servico;
import br.com.dynamic.repo.interfaces.IServicoRepo;

@Repository
public class ServicoRepo extends JpaRepository<Servico, Integer> implements IServicoRepo {
	
	private static final long serialVersionUID = 1L;

	@Override
	public Servico getById(Integer id){
		TypedQuery<Servico> q = getEntityManager().createQuery("from Servico u where u.id = :idServico", Servico.class);
		q.setParameter("idServico", id);
		
		List<Servico> servicos = q.getResultList();
		
		if (servicos.isEmpty()) {
			return null;
		}

		return servicos.get(0);
	}
	
	@Override
	public Integer getInsertedId() {
		return getEntityManager().createQuery("select max(s.id) from Servico s", Integer.class).getSingleResult();
	}
	
	@Override
	public List<Servico> listarPorNome(String nome) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();		
		CriteriaQuery<Servico> q = cb.createQuery(Servico.class);
		Root<Servico> root = q.from(Servico.class);
		EntityType<Servico> type = getEntityManager().getMetamodel().entity(Servico.class);

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
	public List<Servico> listarPorCategoria(Categoria categoria) {
		TypedQuery<Servico> query = getEntityManager().createQuery("select p from Servico p where p.categoria = :categoria", Servico.class);
		query.setParameter("categoria", categoria);

		return query.getResultList();
	}
	
	@Override
	public Servico buscarNomeLista(String nome){
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();		
		CriteriaQuery<Servico> q = cb.createQuery(Servico.class);
		Root<Servico> root = q.from(Servico.class);

		q.where(cb.equal(root.<String>get("nome"), nome));
		q.orderBy(cb.asc(root.get("nome")));

		try {
			return getEntityManager().createQuery(q.select(root)).getSingleResult();
		} catch(NoResultException e) {
			return null;
		}
	}
	
	@Override
	public void remover(Servico servico) {
		Servico servico2 = this.getById(servico.getId());
		getEntityManager().remove(servico2);
	}

}
