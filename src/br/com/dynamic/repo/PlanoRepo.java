package br.com.dynamic.repo;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import br.com.dynamic.entidade.Plano;
import br.com.dynamic.repo.interfaces.IPlanoRepo;

@Repository
public class PlanoRepo implements IPlanoRepo {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Plano buscarPlano() {
		CriteriaBuilder builder = em.getCriteriaBuilder();		
		CriteriaQuery<Plano> query = builder.createQuery(Plano.class);
		Root<Plano> root = query.from(Plano.class);

		try {
			return em.createQuery(query.select(root)).getResultList().get(0);
		} catch(NoResultException e) {
			Plano plano = new Plano();
			plano.setIdPlano(1);
			plano.setNome("Padrao");
			plano.setValor(10);
			plano.setFormula(50);
			
			return plano;
		}
	}

}