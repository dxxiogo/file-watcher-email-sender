package models.entities;

public class Config {
	
	private String main_path;
	private String default_message;
	private String database_path;
	
	
	public Config(String main_path, String default_message, String database_path) {
		this.main_path = main_path;
		this.default_message = default_message;
		this.database_path = database_path;
	}

	public String getMain_path() {
		return main_path;
	}
	
	public void setMain_path(String main_path) {
		this.main_path = main_path;
	}
	
	public String getDefault_message() {
		return default_message;
	}
	
	public void setDefault_message(String default_message) {
		this.default_message = default_message;
	}
	
	public String getDatabase_path() {
		return database_path;
	}
	
	public void setDatabase_path(String database_path) {
		this.database_path = database_path;
	}
	
	
	
}
