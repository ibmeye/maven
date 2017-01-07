package com.duapp.ibmeye.account.captcha;

public class AccountCaptchaException extends Exception {
	public AccountCaptchaException(String message) {
		super(message);
	}
	public AccountCaptchaException(String message, Throwable throwable) {
		super(message,throwable);
	}
}
