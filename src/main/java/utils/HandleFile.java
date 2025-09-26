package utils;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import models.entities.Client;
import models.entities.SPEDFile;


public final class HandleFile {
	
	
	public static Client extractClientInfo (List<Path> filesPath, String fileSufixe) {
		Path infoFile = filesPath.get(0).getParent().getParent().resolve("cliente.properties");
		Properties props = LoadProperties.loadConfig(infoFile.toString());

		String clientName = props.getProperty("cliente");
		String[] emails = props.getProperty("email_contabilidade").split(" ");
		
		List<SPEDFile> files = new ArrayList<>();
		
		for(Path p : filesPath) {
			files.add(new SPEDFile(p, fileSufixe));
			
		}
		
		return new Client(clientName, emails, LocalDate.now(), files);
	
	}
}
