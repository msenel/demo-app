package me.senel.demo.model;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import me.senel.demo.model.dao.AbstractHibernateDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:conf/test-application-context.xml",
		"classpath:conf/test-datasource-context.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
public abstract class AbstractDaoTestCase extends AbstractHibernateDao {
	
	@Before
	public void beforeTest() {
	}

	@After
	public void afterTest() {
	}

}
