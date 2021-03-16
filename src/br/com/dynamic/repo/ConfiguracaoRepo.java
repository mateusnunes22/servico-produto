package br.com.dynamic.repo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.com.dynamic.entidade.Sistema;
import br.com.dynamic.repo.interfaces.IConfiguracaoRepo;

@Repository
public class ConfiguracaoRepo implements IConfiguracaoRepo {
	
	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public String getNomeEmpresa() {
		TypedQuery<Sistema> q = em.createQuery("from Sistema s", Sistema.class);
		return q.getSingleResult().getNome();
	}

	@Override
	public int getNumeroDias() {
		TypedQuery<Sistema> q = em.createQuery("from Sistema s", Sistema.class);
		return q.getSingleResult().getDias();
	}

}