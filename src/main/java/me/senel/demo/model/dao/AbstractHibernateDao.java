package me.senel.demo.model.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

@Transactional
public abstract class AbstractHibernateDao
{
    @Autowired
    private SessionFactory sessionFactory;

    /**
     * @return the current hibernate session of actual thread
     */
    public Session getSession()
    {
        return sessionFactory.getCurrentSession();
    }
    
    /**
     * @return the autowired hibernate session factory
     */
    public SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }

    /**
     * @param sessionFactory - set the hibernate session factory
     */
    public void setSessionFactory(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }
}