package br.com.dynamic.repo.interfaces;

import java.io.Serializable;

import br.com.dynamic.entidade.Auditoria;

public interface IAuditoriaRepo extends Serializable {

	public void auditar(Auditoria auditoria);

}