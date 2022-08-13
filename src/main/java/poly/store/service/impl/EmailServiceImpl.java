package poly.store.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import poly.store.entity.Email;
import poly.store.service.EmailService;



@Service
public class EmailServiceImpl implements EmailService {
	@Autowired
	JavaMailSender javaMailSender;
	
	List<Email> list = new ArrayList<>();
	
	@Override
	public void send(Email email) throws MessagingException {
		// Tạo message
		MimeMessage message = javaMailSender.createMimeMessage();
		// Sử dụng Helper để thiết lập các thông tin cần thiết cho message
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
		helper.setFrom(email.getFrom());
		helper.setTo(email.getTo());
		helper.setSubject(email.getSubject());
		helper.setText(email.getBody(), true);
		helper.setReplyTo(email.getFrom());
		
		String[] cc = email.getCc();
		if(cc != null && cc.length > 0) {
			helper.setCc(cc);
		}
		String[] bcc = email.getBcc();
		if(bcc != null && bcc.length > 0) {
			helper.setBcc(bcc);
		}
		String[] attachments = email.getAttachments();
		if(attachments != null && attachments.length > 0) {
			for(String path: attachments) {
				File file = new File(path);
				helper.addAttachment(file.getName(), file);
			}
		}
		// Gửi message đến SMTP server
		javaMailSender.send(message);
	}

	@Override
	public void send(String to, String subject, String body) throws MessagingException {
		this.send(new Email(to, subject, body));
	}
}
