package com.duapp.ibmeye.account.kaptcha;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.duapp.ibmeye.account.captcha.AccountCaptchaService;


/**
 * Unit test for simple App.
 */
public class AccountCaptchaServiceTest
{
	private AccountCaptchaService service;
	
	@BeforeClass
	public void testBefore() throws Exception {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("account-captcha.xml");
		service = (AccountCaptchaService) ctx.getBean("accountCaptchaService");
	}
	
	@Test
	public void testGenerateCaptcha() throws Exception {
		String captchaKey = service.generateCaptchaKey();
		Assert.assertNotNull(captchaKey);
		
		byte[] captchaImage = service.generateCaptchaImage(captchaKey);
		Assert.assertTrue(captchaImage.length > 0);
		
		File image = new File("target/" + captchaKey + ".jpg");
		OutputStream out = null;
		try {
			out = new FileOutputStream( image );
			out.write( captchaImage );
		}
		finally {
			if( out != null ) {
				try {
					out.close();
				}
				catch (Exception e) {
					throw e;
				}
			}
		}
		Assert.assertTrue( image.exists() && image.length() > 0 );
		
	}
	
	@Test
	public void testValidateCaptchaCorrect() throws Exception {
		List<String> preDefinedTexts = new ArrayList<String>();
		preDefinedTexts.add("123456");
		preDefinedTexts.add("abcdef");
		service.setPreDefinedTexts(preDefinedTexts);
		
		String captchaKey = service.generateCaptchaKey();
		service.generateCaptchaImage(captchaKey);
		Assert.assertTrue( service.validateCaptcha(captchaKey, "123456") );
		
		captchaKey = service.generateCaptchaKey();
		service.generateCaptchaImage(captchaKey);
		Assert.assertTrue( service.validateCaptcha(captchaKey, "abcdef") );
	}
	
	@Test
	public void testValidateCaptchaIncorrect() throws Exception {
		List<String> preDefinedTexts = new ArrayList<String>();
		preDefinedTexts.add("123456");
		service.setPreDefinedTexts(preDefinedTexts);
		
		String captchaKey = service.generateCaptchaKey();
		service.generateCaptchaImage(captchaKey);
		Assert.assertFalse( service.validateCaptcha(captchaKey, "678910") );
	}
}
