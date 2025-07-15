package models.services;

import java.nio.file.Files;
import java.util.Base64;
import java.util.List;

import db.DB;
import io.mailtrap.client.MailtrapClient;
import io.mailtrap.config.MailtrapConfig;
import io.mailtrap.factory.MailtrapClientFactory;
import io.mailtrap.model.request.emails.Address;
import io.mailtrap.model.request.emails.EmailAttachment;
import io.mailtrap.model.request.emails.MailtrapMail;
import models.entities.ClientSPEDFileInfo;
import models.interfaces.MailService;

public class MailtrapService implements MailService {

	private static final String TOKEN = DB.loadProperties().getProperty("MAIL_SERVICE_API_KEY");

	@Override
	public void sendEmail(ClientSPEDFileInfo clientFile) {
		try {
			MailtrapConfig config = new MailtrapConfig.Builder().token(TOKEN).build();
			MailtrapClient client = MailtrapClientFactory.createMailtrapClient(config);

			byte[] fileBytes = Files.readAllBytes(clientFile.getPath());

			String encodedFile = Base64.getEncoder().encodeToString(fileBytes);
			System.out.println("Valor retornado em getPath() " + clientFile.getPath());
			System.out.println("Tamanho do array de bytes " + fileBytes.length);

			EmailAttachment att = EmailAttachment.builder()
									.content(encodedFile)
									.filename("SPED.txt")
									.type("application/txt")
									.disposition("attachment")
									.build();
			MailtrapMail mail = MailtrapMail.builder()
									.from(new Address("hello@demomailtrap.co", "Mailtrap"))
									.text("Olá! Segue o arquivo SPED em anexo.")
									.to(List.of(new Address(clientFile.getEmail()))).subject(clientFile.toString())
									.attachments(List.of(att)).build();

			System.out.println(client.send(mail));
		} catch (Exception e) {
			System.err.println("Error sending email: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
