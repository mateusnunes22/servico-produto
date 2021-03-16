package br.com.dynamic.services;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import br.com.dynamic.entidade.Cliente;

@Service
public class ClienteService {
	
	@PersistenceContext
	private EntityManager em;
	
	public Cliente getClientePorNome(String nome) {
		Cliente cliente = null;
		
		Query q = em.createQuery("from Cliente u where u.nome = :nome");
		q.setParameter("nome", nome);
		
		try {
			cliente = (Cliente) q.getSingleResult();
		} catch (NoResultException e) {
		}
		
		return cliente; 
	}
	
	

}
