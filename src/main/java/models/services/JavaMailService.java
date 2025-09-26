package models.services;

import java.time.LocalDateTime;
import java.util.Properties;

import db.DB;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import models.dao.DaoFactory;
import models.dao.SendDao;
import models.entities.Client;
import models.entities.Log;
import models.entities.SPEDFile;
import models.entities.Send;
import models.entities.enums.LogStatus;
import models.interfaces.LogService;
import models.interfaces.MailService;

public class JavaMailService implements MailService{

	private final Properties config = DB.loadProperties();
	private SendDao sendDao = DaoFactory.createDaoJDBC();
	private LogService logService;
	
	
	
	public JavaMailService(LogService logService) {
		this.logService = logService;
	}

	@Override
	public void sendEmail(Client client) {
		
		Properties props = new Properties();
		props.put("mail.smtp.host", config.getProperty("mail.smtp.host"));
		props.put("mail.smtp.port",  config.getProperty("mail.smtp.port"));
		props.put("mail.smtp.auth",  config.getProperty("mail.smtp.auth"));
		props.put("mail.smtp.starttls.enable",  config.getProperty("mail.smtp.starttls.enable"));
		props.put("mail.smtp.connectiontimeout", "10000");
		props.put("mail.smtp.timeout", "10000");     
		
		Session session = Session.getInstance(props, new Authenticator() {
			
			  protected PasswordAuthentication getPasswordAuthentication() {
			    return new PasswordAuthentication(config.getProperty("mail.smtp.user"), config.getProperty("mail.smtp.password"));
			  }
			  
			});

		
		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(config.getProperty("mail.smtp.user")));
			
			for(String email : client.getEmails()) {
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			}
			
		
			MimeBodyPart messageBodyPart = new MimeBodyPart();
	        messageBodyPart.setText("Olá! Segue os arquivos em anexo.");
	           
        	Multipart multipart = new MimeMultipart();


        	for (SPEDFile file : client.getFiles()) {
        	    MimeBodyPart attachmentPart = new MimeBodyPart();
        	    DataSource source = new FileDataSource(file.getPath().toFile());
        	    attachmentPart.setDataHandler(new DataHandler(source));
        	    attachmentPart.setFileName(file.getPath().getFileName().toString());
        	    multipart.addBodyPart(attachmentPart);
        	}
        	
        	message.setSubject(client.toString());
        	message.setContent(multipart);

	        
			Transport.send(message);
			
			sendDao.create(new Send(LocalDateTime.now(), client.getEmails()[0], client.getFiles().get(0).getPath().toFile().getName(), "Enviado com sucesso!"));
			
			logService.registerLog(new Log(LocalDateTime.now(), String.format(" Enviado com sucesso! Destinatário: %s ", client.getEmails()[0]), LogStatus.SUCCESS));
		} catch (MessagingException e) {
			logService.registerLog(new Log(LocalDateTime.now(), e.getMessage(), LogStatus.ERROR));
		}
	
	}

}
