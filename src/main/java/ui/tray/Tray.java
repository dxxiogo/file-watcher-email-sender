package ui.tray;

import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Properties;

import ui.settings.SettingsUi;

public class Tray {

	 public static TrayIcon createTrayIcon(Properties config) {

	        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");

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
