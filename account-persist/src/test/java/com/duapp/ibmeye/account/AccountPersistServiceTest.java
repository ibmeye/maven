package com.duapp.ibmeye.account;

import java.io.File;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.duapp.ibmeye.account.persist.Account;
import com.duapp.ibmeye.account.persist.AccountPersistService;

public class AccountPersistServiceTest {
	private AccountPersistService service;
	
	@BeforeClass
	public void testBefore() throws Exception {
		File dataFile = new File("target/test-classes/persist-data.xml");
		
		if( dataFile.exists() ) {
			dataFile.delete();
		}
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("account-persist.xml");
		
		service = (AccountPersistService)ctx.getBean("accountPersistService");
		
		Account account = new Account();
		account.setId("15656070630");
		account.setEmail("ibmeye@163.com");
		account.setPassword("1123456");
		account.setName("ibmeye");
		account.setActivated(true);
		
		service.createAccount(account);
		
	}
	
	@Test
	public void test() throws Exception {
		Account account = service.readAccount("15656070630");
		Assert.assertNotNull(account);
		Assert.assertEquals("ibmeye@163.com", account.getEmail());
		Assert.assertEquals("1123456", account.getPassword());
		Assert.assertEquals("ibmeye", account.getName());
		Assert.assertEquals(true, account.isActivated());
	}
}
