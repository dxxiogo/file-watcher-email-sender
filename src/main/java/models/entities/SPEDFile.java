package models.entities;

import java.nio.file.Path;

public class SPEDFile {
		
	private Path path;
	private String sufixe;
    
	public SPEDFile(Path path, String sufixe) {
		this.path = path;
		this.sufixe = sufixe;
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public String getSufixe() {
		return sufixe;
	}

	public void setSufixe(String sufixe) {
		this.sufixe = sufixe;
	}

	
}
