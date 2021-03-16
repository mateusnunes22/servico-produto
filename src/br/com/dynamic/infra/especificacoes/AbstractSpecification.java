package br.com.dynamic.infra.especificacoes;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public abstract class AbstractSpecification<T> implements PredicateSpecification<T> {

	@Override
	public Predicate toPredicate(Root<T> poll, CriteriaBuilder cb) {
		throw new NotImplementedException();
	}

}