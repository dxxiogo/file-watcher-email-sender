package utils;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;

import models.entities.ClientSPEDFileInfo;
import models.entities.Log;
import models.entities.enums.FileStatus;
import models.entities.enums.LogStatus;
import models.interfaces.LogService;

public final class HandleFile {
	
	private LogService logService;
	
	public HandleFile (LogService logService) {
		this.logService = logService;
	}
	
	public ClientSPEDFileInfo extractClientInfo (Path filePath) {
		
		try {
			Path propsPath = filePath.getParent().resolve("cliente.properties");
			String parentFolderName = filePath.getParent().getFileName().toString();
			if(!Files.isRegularFile(propsPath) && !Files.exists(propsPath)) {
				throw new FileNotFoundException();
			}
			String[] fields = parentFolderName.split(" ");
			String clientName = fields[0];
			String emailReplaced = fields[1].replace("(", "");
			String email = emailReplaced.replace(")", "");
			String fileStatus = filePath.getFileName().toString().contains("VALIDADO") ? "VALIDADO" : "RETIFICADO";
			
			return new ClientSPEDFileInfo(clientName, email, LocalDate.now(), FileStatus.valueOf(fileStatus), filePath);
		} catch (Exception e) {
			logService.registerLog(new Log(LocalDateTime.now(), e.getMessage(), LogStatus.ERROR));
			return null;
		}
		
	}
	
	
}
