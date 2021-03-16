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
import br.com.dynamic.entidade.Produto;
import br.com.dynamic.repo.interfaces.IProdutoRepo;

@Repository
public class ProdutoRepo extends JpaRepository<Produto, Integer> implements IProdutoRepo {
	
	@Override
	public Produto getById(Integer id) {
		TypedQuery<Produto> q = getEntityManager().createQuery("from Produto u where u.id = :idProduto", Produto.class);
		q.setParameter("idProduto", id);
		
		List<Produto> produtos = q.getResultList();
		
		if (produtos.isEmpty()) {
			return null;
		}

		return produtos.get(0);
	}
	
	@Override
	public Integer getInsertedId() {
		return getEntityManager().createQuery("select max(p.id) from Produto p", Integer.class).getSingleResult();
	}
	
	@Override
	public List<Produto> findAllByNome(String nome){
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();		
		CriteriaQuery<Produto> q = cb.createQuery(Produto.class);
		Root<Produto> root = q.from(Produto.class);
		EntityType<Produto> type = getEntityManager().getMetamodel().entity(Produto.class);

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
	public Produto findByNome(String nome){		
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();		
		CriteriaQuery<Produto> q = cb.createQuery(Produto.class);
		Root<Produto> root = q.from(Produto.class);

		q.where(cb.equal(root.<String>get("nome"), nome));

		try {
			return getEntityManager().createQuery(q.select(root)).getSingleResult();
		} catch(NoResultException nre) {
			return null;
		}
	}

	@Override
	public List<Produto> findAllByCategoria(Categoria categoria) {
		TypedQuery<Produto> query = getEntityManager().createQuery("select p from Produto p where p.categoria = :categoria", Produto.class);
		query.setParameter("categoria", categoria);

		return query.getResultList();
	}

}
