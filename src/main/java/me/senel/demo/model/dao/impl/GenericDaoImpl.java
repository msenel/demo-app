package me.senel.demo.model.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import me.senel.demo.model.dao.AbstractHibernateDao;
import me.senel.demo.model.dao.GenericDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public abstract class GenericDaoImpl<T> extends AbstractHibernateDao implements GenericDao<T> {

	private Class<T> type;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public GenericDaoImpl() {
		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		type = (Class) pt.getActualTypeArguments()[0];
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public T save(T t) {
		
		getSession().saveOrUpdate(t);
		return t;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(Object obj) {
		getSession().delete(obj);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public T get(Object id) {
		return (T) getSession().get(type, (Serializable) id);
	}



}
