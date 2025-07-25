package ui.settings;

import java.awt.GridLayout;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import utils.LoadProperties;

public class SettingsUi {
	
	public static void showSettingsWindow(Properties config) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Configurações");
            frame.setSize(500, 200);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLayout(new GridLayout(3, 3, 10, 10));
           
            JTextField dbServer = new JTextField(config.getProperty("db.server", ""));
            JTextField dbPort = new JTextField(config.getProperty("db.port", ""));
            JTextField dbField = new JTextField(config.getProperty("db.path", ""));
            dbField.setEditable(false);
            JButton dbBrowse = new JButton("Browse");
            dbBrowse.addActionListener(e -> {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    dbField.setText(fileChooser.getSelectedFile().getAbsolutePath());
                }
            });

            JTextField dirField = new JTextField(config.getProperty("monitorDir", ""));
            dirField.setEditable(false);
            JButton dirBrowse = new JButton("Browse");
            dirBrowse.addActionListener(e -> {
                JFileChooser dirChooser = new JFileChooser();
                dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int result = dirChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    dirField.setText(dirChooser.getSelectedFile().getAbsolutePath());
                }
            });

            JButton saveBtn = new JButton("Salvar");
            saveBtn.addActionListener(e -> {
                config.setProperty("db.path", dbField.getText());
                config.setProperty("db.port", dbPort.getText());
                config.setProperty("db.server", dbServer.getText());
                config.setProperty("monitorDir", dirField.getText());
                saveConfig(config);
                JOptionPane.showMessageDialog(frame, "Configurações salvas com sucesso!");
                frame.dispose();
            });

            frame.add(new JLabel("Servidor"));
            frame.add(dbServer);
            
            frame.add(new JLabel("Porta"));
            frame.add(dbPort);
            
            frame.add(new JLabel("Banco de dados:"));
            frame.add(dbField);
            frame.add(dbBrowse);

            frame.add(new JLabel("Pasta monitorada:"));
            frame.add(dirField);
            frame.add(dirBrowse);

            frame.add(new JLabel());
            frame.add(new JLabel());
            frame.add(saveBtn);

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
	
	
	  private static void saveConfig(Properties props) {
	        try (FileOutputStream out = new FileOutputStream(LoadProperties.CONFIG_FILE)) {
	            props.store(out, "App Configuration");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

}
