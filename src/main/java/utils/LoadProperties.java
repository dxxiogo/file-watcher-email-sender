package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LoadProperties {
	
	public final static String CONFIG_FILE = "config.properties"; 
	
	 public static Properties loadConfig(String propsFile) {
	        Properties props = new Properties();
	        File file = new File(propsFile);
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
