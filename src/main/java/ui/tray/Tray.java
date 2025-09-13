package ui.tray;

import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.TrayIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.ImageIO;

import ui.settingsUi.SettingsUi;

public class Tray {

	 public static TrayIcon createTrayIcon(Properties config) {

		 	File pathToFile = new File("C:\\folder-watcher-email-sender\\icon.png");
//	        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
		 	Image image = null;
			try {
				image = ImageIO.read(pathToFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
	        PopupMenu popup = new PopupMenu();

	        MenuItem exitItem = new MenuItem("Sair");
	        exitItem.addActionListener(e -> {
	            System.exit(0);
	        });
	        popup.add(exitItem);

	        TrayIcon trayIcon = new TrayIcon(image, "Folder Watcher Email Sender", popup);
	        trayIcon.setImageAutoSize(true);

	        trayIcon.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	                if (e.getButton() == MouseEvent.BUTTON1) {
	                	SettingsUi.showSettingsWindow(config);
	                }
	            }
	        });

	        return trayIcon;
	    }
}
