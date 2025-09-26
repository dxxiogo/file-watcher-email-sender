package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LoadProperties {
	
	public final static String CONFIG_FILE = "config.properties"; 
	
	public static Properties loadConfig(String filePath) {
	    Properties props = new Properties();
	    try (FileInputStream fis = new FileInputStream(filePath)) {
	        props.load(fis);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return props;
	}

	  
}
