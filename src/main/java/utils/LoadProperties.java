package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LoadProperties {
	
	public static final String CONFIG_FILE = "config.properties";
	
	
	 public static Properties loadConfig() {
	        Properties props = new Properties();
	        File file = new File(CONFIG_FILE);
	        if (file.exists()) {
	            try (FileInputStream in = new FileInputStream(file)) {
	                props.load(in);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        return props;
	    }

	  
}
