package utils;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import models.entities.Client;
import models.entities.SPEDFile;
import models.entities.enums.FileStatus;
import models.entities.enums.FileType;

public final class HandleFile {
	
	
	public static Client extractClientInfo (List<Path> filesPath) {
		Path infoFile = filesPath.get(0).getParent().getParent().resolve("cliente.properties");
		Properties props = LoadProperties.loadConfig(infoFile.toString());

		String clientName = props.getProperty("cliente");
		String[] emails = props.getProperty("email_contabilidade").split(" ");
		
		List<SPEDFile> files = new ArrayList<>();
		
		for(Path p : filesPath) {
			String fileType = p.getFileName().toString().toUpperCase().contains("SPED") ? "SPED" : "CONTRIBUICOES";
			String fileStatus = p.getFileName().toString().contains("VALIDADO") ? "VALIDADO" : "RETIFICADO";
			files.add(new SPEDFile(p, FileType.valueOf(fileType), FileStatus.valueOf(fileStatus)));
			
		}
		
		return new Client(clientName, emails, LocalDate.now(), files);
	
	}
}
