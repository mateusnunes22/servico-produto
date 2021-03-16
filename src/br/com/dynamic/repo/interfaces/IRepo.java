package br.com.dynamic.repo.interfaces;

import java.io.Serializable;
import java.util.List;

public interface IRepo<T, ID extends Serializable> {

	public T getById(ID id);
	
	public ID getInsertedId();

	public List<T> getAll();

	public void add(T entity);

	public void update(T entity);

	public Integer countAll();

}