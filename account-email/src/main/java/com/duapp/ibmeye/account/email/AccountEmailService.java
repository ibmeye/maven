package com.duapp.ibmeye.account.email;

public interface AccountEmailService {
	int c = 2;
	void sendEmail(String to, String subject, String htmlText);

}
