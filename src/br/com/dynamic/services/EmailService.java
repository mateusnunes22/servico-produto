package br.com.dynamic.services;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements IEmailService {

	@Autowired
	private MailSender mailSender;

	@Autowired
	private SimpleMailMessage preConfiguredMessage;

	public void sendMail(String subject, String msg) {
		SimpleMailMessage mailMessage = new SimpleMailMessage(preConfiguredMessage);
		mailMessage.setSubject(subject);
		mailMessage.setText(msg);

		try {
			mailSender.send(mailMessage);
		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).log(Level.ERROR, e);
		}
	}

}