package br.com.dynamic.repo;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;

import org.springframework.stereotype.Repository;

import br.com.dynamic.entidade.Categoria;
import br.com.dynamic.repo.interfaces.ICategoriaRepo;

@Repository
public class CategoriaRepo extends JpaRepository<Categoria, Integer> implements ICategoriaRepo {

	@Override
	public Integer getInsertedId() {
		return getEntityManager().createQuery("select max(c.id) from Categoria c", Integer.class).getSingleResult();
	}

	@Override
	public Categoria findByDescricao(String descricao) {
		TypedQuery<Categoria> q = getEntityManager().createQuery("from Categoria u where u.descricao = : descricao", Categoria.class);
		q.setParameter("descricao", descricao);
		
		return q.getSingleResult();
	}

	@Override
	public List<Categoria> findAllByDescricao(String descricao) {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();		
		CriteriaQuery<Categoria> query = builder.createQuery(Categoria.class);
		Root<Categoria> root = query.from(Categoria.class);
		EntityType<Categoria> type = getEntityManager().getMetamodel().entity(Categoria.class);
        
        query.where(
			builder.like(
					builder.lower(root.get(type.getDeclaredSingularAttribute("descricao", String.class))), 
					"%" + descricao.toLowerCase() + "%"
			)
        );
		query.orderBy(builder.asc(root.get("descricao")));

		return getEntityManager().createQuery(query.select(root)).getResultList();
	}
	
}