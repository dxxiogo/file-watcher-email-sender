package models.interfaces;

import models.entities.ClientSPEDFileInfo;

public interface MailService {
	
	public void sendEmail(ClientSPEDFileInfo clientFile);
}
