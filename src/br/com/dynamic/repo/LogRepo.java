package br.com.dynamic.repo;

import br.com.dynamic.entidade.Log;

public class LogRepo extends JpaRepository<Log, Integer> {

	@Override
	public Integer getInsertedId() {
		return getEntityManager().createQuery("select max(l.id) from Log l", Integer.class).getSingleResult();
	}

}