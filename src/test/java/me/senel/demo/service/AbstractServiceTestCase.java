package me.senel.demo.service;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:conf/test-application-context.xml",
		"classpath:conf/test-datasource-context.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
public abstract class AbstractServiceTestCase {
	@Before
	public void beforeTest() {
	}

	@After
	public void afterTest() {
	}

}
