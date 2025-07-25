package models.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import db.DB;
import models.entities.Log;
import models.interfaces.LogService;

public class LogFileService implements LogService{
	
	private final Properties config = DB.loadProperties();

	public void registerLog(Log log) {

		File outputFolder = null;
		File output = null;
		
		String now = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMYYYY"));
		
		switch (log.getStatus()) {
			case ERROR:
				outputFolder = new File(config.getProperty("errorLogPath"));
				output = new File(outputFolder, "log_error_" + now);
				break;
			case SUCCESS:
				outputFolder = new File(config.getProperty("logPath"));
				output = new File(outputFolder, "log_" + now);
				break;
			case WARN:
				outputFolder = new File(config.getProperty("logPath"));
				output = new File(outputFolder, "log_warn_" + now);
		default:
			break; 
				
		}
		
		
		if (!outputFolder.exists()) {
			outputFolder.mkdir();
		}

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(output, true))) {
			bw.write(String.format("%s - %s", log.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy_HH:mm:ss")), log.getDescription()));
			bw.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
