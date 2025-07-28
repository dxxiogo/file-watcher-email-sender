import java.awt.AWTException;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Properties;

import models.entities.Log;
import models.entities.enums.LogStatus;
import models.interfaces.LogService;
import models.services.JavaMailService;
import models.services.LogFileService;
import models.services.WacthFolderService;
import ui.tray.Tray;
import utils.LoadProperties;

public class Main {

	public static void main(String[] args) {
		
		Path rootPath = Paths.get(LoadProperties.loadConfig().getProperty("monitorDir"));
		WacthFolderService watcher;
		LogService logService = new LogFileService();
		
		try {
			if (!SystemTray.isSupported()) {
	            logService.registerLog(new Log(LocalDateTime.now(), "System tray not supported", LogStatus.ERROR));
	            return;
	        }

	        Properties config = LoadProperties.loadConfig();

	        TrayIcon trayIcon = Tray.createTrayIcon(config);
	        SystemTray tray = SystemTray.getSystemTray();
			tray.add(trayIcon);
			
			watcher = new WacthFolderService(rootPath, new JavaMailService(new LogFileService()));
			watcher.processEvents();
		} catch (IOException e) {
			logService.registerLog(new Log(LocalDateTime.now(), e.getMessage(), LogStatus.ERROR));
		}catch (AWTException e) {
			logService.registerLog(new Log(LocalDateTime.now(), e.getMessage(), LogStatus.ERROR));
        }
		

	}

}
