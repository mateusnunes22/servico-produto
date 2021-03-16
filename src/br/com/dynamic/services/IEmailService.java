package br.com.dynamic.services;

import org.springframework.mail.MailSender;

public interface IEmailService {
	
	public void sendMail(String subject, String msg);

}