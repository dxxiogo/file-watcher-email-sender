package utils;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDate;

import models.entities.ClientSPEDFileInfo;
import models.entities.enums.FileStatus;

public final class HandleFile {
	
	
	public static ClientSPEDFileInfo extractClientInfo (Path filePath) {
		String parentFolderName = filePath.getParent().getFileName().toString();
		String[] fields = parentFolderName.split(" ");
		String clientName = fields[0];
		String emailReplaced = fields[1].replace("(", "");
		String email = emailReplaced.replace(")", "");
		return new ClientSPEDFileInfo(clientName, email, LocalDate.now(), FileStatus.VALIDADO, filePath);
	}
	
	
}
