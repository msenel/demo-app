package me.senel.demo.model.dao;


public interface GenericDao<T> {

	T save(T t);

	void delete(Object obj);

	T get(Object id);
	
}
