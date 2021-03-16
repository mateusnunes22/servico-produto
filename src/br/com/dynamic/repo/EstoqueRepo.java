package br.com.dynamic.repo;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.dynamic.entidade.Estoque;
import br.com.dynamic.entidade.Produto;
import br.com.dynamic.repo.interfaces.IEstoqueRepo;

@Repository
public class EstoqueRepo extends JpaRepository<Estoque, Integer> implements IEstoqueRepo {
	
	private static final long serialVersionUID = 1L;

	@Override
	public Estoque getById(Integer id) {
		TypedQuery<Estoque> q = getEntityManager().createQuery("from Estoque u where u.id.produtoIdProduto = :produtoIdProduto", Estoque.class);
		q.setParameter("produtoIdProduto", id);
		
		return q.getSingleResult();
	}

	public Integer getInsertedId() {
		return getEntityManager().createQuery("select max(e.id.produtoIdProduto) from Estoque e", Integer.class).getSingleResult();
	}
	
	@Override
	public Estoque findByProduto(Produto produto){
		TypedQuery<Estoque> q = getEntityManager().createQuery("from Estoque u where u.id.produtoIdProduto = :produtoIdProduto", Estoque.class);
		q.setParameter("produtoIdProduto", produto.getId());
		
		return q.getSingleResult();
	} 
	
	@Override
	public List<Estoque> findAllByProduto(Produto produto) {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();		
		CriteriaQuery<Estoque> query = builder.createQuery(Estoque.class);
		Root<Estoque> root = query.from(Estoque.class);

		query.where(builder.equal(root.<String>get("produto"), produto));

		return getEntityManager().createQuery(query.select(root)).getResultList();		
	}

	@Override
	public List<Estoque> estoqueBaixo() {
		return getEntityManager().createQuery("from Estoque e where e.quantidadeAtual <= e.quantidadeBaixa", Estoque.class).getResultList();
	} 
	
	@Override
	@Transactional
	public Estoque baixarEstoque(Estoque estoque) {
		estoque.setQuantidadeAtual(estoque.getQuantidadeAtual() - 1);
		super.update(estoque);

		return estoque;
	}
	
}
