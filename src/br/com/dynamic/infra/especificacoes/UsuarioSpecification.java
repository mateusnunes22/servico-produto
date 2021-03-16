package br.com.dynamic.infra.especificacoes;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.dynamic.entidade.Usuario;

public class UsuarioSpecification implements PredicateSpecification<Usuario> {
	
	private String name;

	public UsuarioSpecification(String name) {
		this.name = name;
	}
	
	@Override
	public Predicate toPredicate(Root<Usuario> root, CriteriaBuilder cb) {
		return cb.equal(root.get("nome"), this.name);
	}

}
