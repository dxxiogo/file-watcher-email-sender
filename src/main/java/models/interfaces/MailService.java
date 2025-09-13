package models.interfaces;

import models.entities.Client;

public interface MailService {
	
	public void sendEmail(Client clientFile);
}
