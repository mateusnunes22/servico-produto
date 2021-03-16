package br.com.dynamic.repo;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import br.com.dynamic.entidade.Auditoria;
import br.com.dynamic.repo.interfaces.IAuditoriaRepo;
import br.com.dynamic.repo.interfaces.IRepo;

public abstract class JpaRepository<T, ID extends Serializable> implements IRepo<T, ID> {

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private IAuditoriaRepo auditoriaRepo;
	
	@Override
	public T getById(ID id) {
		return em.find(this.getType(), id);
	}

	@Override
	public List<T> getAll() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
	    CriteriaQuery<T> query = builder.createQuery(this.getType());
	    Root<T> root = query.from(this.getType());
	    query.select(root);

	    return em.createQuery(query).getResultList();
	}

	@Override
	@Transactional
	public void add(T entity) {
		em.merge(entity);
		
		Auditoria audit = new Auditoria();
		audit.preencherAuditoria(Auditoria.ADICAO, getType().getSimpleName(), (Integer) getInsertedId());
		
		auditoriaRepo.auditar(audit);
	}

	@Override
	@Transactional
	public void update(T entity) {
		entity = em.merge(entity);
		em.flush();
		
		Auditoria audit = new Auditoria();
		audit.preencherAuditoria(Auditoria.EDICAO, getType().getSimpleName(), (Integer) getInsertedId());
		
		auditoriaRepo.auditar(audit);
	}

	@Override
	public Integer countAll() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		cq.select(cb.count(cq.from(this.getType())));

		return em.createQuery(cq).getSingleResult().intValue();
	}
	
	@SuppressWarnings("unchecked")
	public Class<T> getType() {
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		return (Class<T>) type.getActualTypeArguments()[0];
	}

	public EntityManager getEntityManager() {
		return em;
	}
	
}