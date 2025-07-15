import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import models.services.MailtrapService;
import models.services.WacthFolderService;

public class Main {

	public static void main(String[] args) {
		Path rootPath = Paths.get("C:\\projeto_java");
		WacthFolderService watcher;
		
		try {
			watcher = new WacthFolderService(rootPath, new MailtrapService());
			watcher.processEvents();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}

}
