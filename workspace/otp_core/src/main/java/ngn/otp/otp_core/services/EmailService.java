package ngn.otp.otp_core.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import ngn.otp.otp_core.ApplicationContextProvider;
import ngn.otp.otp_core.utils.PropUtil;


@Service
public class EmailService {
	private Logger logger = LoggerFactory.getLogger(EmailService.class);
	  private JavaMailSender javaMailSender;
	  private PropUtil prop = ApplicationContextProvider.getContext().getBean(PropUtil.class);
	
	public EmailService(JavaMailSender javaMailSender) {
		logger.info("mail service start...");
		this.javaMailSender = javaMailSender;
		
	}
	
	public void sendEmail(String to, String subject, String content) {
   		
		boolean multipart = true;
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");
			message.setContent(content, "text/html; charset=utf-8");
			helper.setTo(to);
			helper.setSubject(subject);
			message.setFrom(prop.get("spring.mail.username"));
			javaMailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

    }

}
