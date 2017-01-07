package com.duapp.ibmeye.account;

import javax.mail.Message;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.duapp.ibmeye.account.email.AccountEmailService;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
	private GreenMail greenMail;
	
	@BeforeMethod
	public void testBefore() throws Exception {
		greenMail = new GreenMail(ServerSetup.SMTP);
		greenMail.setUser("test@163.com", "123456");
		greenMail.start();
		System.out.println(greenMail.getSmtp().toString());
	}
	
	@Test
	public void testSendMail() throws Exception {
		
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("account-email.xml");
		AccountEmailService accountEmailService = (AccountEmailService) ctx.getBean("accountEmailService");
		
		String subject = "Test Subject";
		String htmlText = "<h3>Test</h3>";
		
		accountEmailService.sendEmail("test@163.com", subject, htmlText);
		
		greenMail.waitForIncomingEmail(2000,1);
		
		Message[] msgs = greenMail.getReceivedMessages();
		
		Assert.assertEquals(1, msgs.length);
		Assert.assertEquals(subject, msgs[0].getSubject());
		Assert.assertEquals(htmlText,GreenMailUtil.getBody(msgs[0]).trim());
	}
	
	@AfterMethod
	public void testAfter() throws Exception {
		greenMail.stop();
	}
}
