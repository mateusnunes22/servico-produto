package br.com.dynamic.infra.especificacoes;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface PredicateSpecification<T> {

	Predicate toPredicate(Root<T> root, CriteriaBuilder cb);

}