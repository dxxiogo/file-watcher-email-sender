package ui.settingsUi;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.*;

import utils.LoadProperties;

public class SettingsUi {

    public static void showSettingsWindow(Properties config) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Configurações");
            frame.setSize(550, 350);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.setBackground(new Color(210, 225, 250)); // azul claro

            Dimension labelSize = new Dimension(130, 25);
            Dimension inputSize = new Dimension(300, 25);

            JPanel serverPanel = new JPanel();
            serverPanel.setBackground(mainPanel.getBackground());
            serverPanel.setLayout(new BoxLayout(serverPanel, BoxLayout.X_AXIS));
            JLabel serverLabel = new JLabel("Servidor:");
            serverLabel.setPreferredSize(labelSize);
            JTextField dbServer = new JTextField(config.getProperty("db.server", ""));
            dbServer.setMaximumSize(inputSize);
            serverPanel.add(serverLabel);
            serverPanel.add(Box.createRigidArea(new Dimension(10, 0)));
            serverPanel.add(dbServer);

            JPanel portPanel = new JPanel();
            portPanel.setBackground(mainPanel.getBackground());
            portPanel.setLayout(new BoxLayout(portPanel, BoxLayout.X_AXIS));
            JLabel portLabel = new JLabel("Porta:");
            portLabel.setPreferredSize(labelSize);
            JTextField dbPort = new JTextField(config.getProperty("db.port", ""));
            dbPort.setMaximumSize(inputSize);
            portPanel.add(portLabel);
            portPanel.add(Box.createRigidArea(new Dimension(10, 0)));
            portPanel.add(dbPort);

            JPanel dbPanel = new JPanel();
            dbPanel.setBackground(mainPanel.getBackground());
            dbPanel.setLayout(new BoxLayout(dbPanel, BoxLayout.X_AXIS));
            JLabel dbLabel = new JLabel("Banco de dados:");
            dbLabel.setPreferredSize(labelSize);
            JTextField dbField = new JTextField(config.getProperty("db.path", ""));
            dbField.setEditable(false);
            dbField.setMaximumSize(inputSize);
            JButton dbBrowse = new JButton("Browse");
            dbPanel.add(dbLabel);
            dbPanel.add(Box.createRigidArea(new Dimension(10, 0)));
            dbPanel.add(dbField);
            dbPanel.add(Box.createRigidArea(new Dimension(10, 0)));
            dbPanel.add(dbBrowse);

            JPanel dirPanel = new JPanel();
            dirPanel.setBackground(mainPanel.getBackground());
            dirPanel.setLayout(new BoxLayout(dirPanel, BoxLayout.X_AXIS));
            JLabel dirLabel = new JLabel("Pasta monitorada:");
            dirLabel.setPreferredSize(labelSize);
            JTextField dirField = new JTextField(config.getProperty("monitorDir", ""));
            dirField.setEditable(false);
            dirField.setMaximumSize(inputSize);
            JButton dirBrowse = new JButton("Browse");
            dirPanel.add(dirLabel);
            dirPanel.add(Box.createRigidArea(new Dimension(10, 0)));
            dirPanel.add(dirField);
            dirPanel.add(Box.createRigidArea(new Dimension(10, 0)));
            dirPanel.add(dirBrowse);

            JButton saveBtn = new JButton("Salvar");
            saveBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
            saveBtn.addActionListener(e -> {
                config.setProperty("db.path", dbField.getText());
                config.setProperty("db.port", dbPort.getText());
                config.setProperty("db.server", dbServer.getText());
                config.setProperty("monitorDir", dirField.getText());
                saveConfig(config);
                JOptionPane.showMessageDialog(frame, "Configurações salvas com sucesso!");
                frame.dispose();
            });

            dbBrowse.addActionListener(e -> {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    dbField.setText(fileChooser.getSelectedFile().getAbsolutePath());
                }
            });

            dirBrowse.addActionListener(e -> {
                JFileChooser dirChooser = new JFileChooser();
                dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int result = dirChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    dirField.setText(dirChooser.getSelectedFile().getAbsolutePath());
                }
            });

            mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            mainPanel.add(serverPanel);
            mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            mainPanel.add(portPanel);
            mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            mainPanel.add(dbPanel);
            mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            mainPanel.add(dirPanel);
            mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            mainPanel.add(saveBtn);
            mainPanel.add(Box.createVerticalGlue());

            frame.setContentPane(mainPanel);
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
