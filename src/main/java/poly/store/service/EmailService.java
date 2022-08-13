package poly.store.service;

import javax.mail.MessagingException;

import poly.store.entity.Email;

public interface EmailService {
	void send(Email email) throws MessagingException;
	void send(String to, String subject, String body) throws MessagingException;

}
