import java.awt.AWTException;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import models.services.JavaMailService;
import models.services.LogFileService;
import models.services.WacthFolderService;
import ui.tray.Tray;
import utils.LoadProperties;

public class Main {

	public static void main(String[] args) {
		
		Path rootPath = Paths.get(LoadProperties.loadConfig().getProperty("monitorDir"));
		WacthFolderService watcher;
		
		try {
			if (!SystemTray.isSupported()) {
	            System.err.println("System tray not supported");
	            return;
	        }

	        Properties config = LoadProperties.loadConfig();

	        TrayIcon trayIcon = Tray.createTrayIcon(config);
	        SystemTray tray = SystemTray.getSystemTray();
			tray.add(trayIcon);
			
			watcher = new WacthFolderService(rootPath, new JavaMailService(new LogFileService()));
			watcher.processEvents();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (AWTException e) {
            System.err.println("TrayIcon could not be added.");
        }
		

	}

}
