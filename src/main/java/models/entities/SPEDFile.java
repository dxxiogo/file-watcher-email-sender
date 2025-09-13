package models.entities;

import java.nio.file.Path;

import models.entities.enums.FileStatus;
import models.entities.enums.FileType;

public class SPEDFile {
		
	private Path path;
    private FileType fileType;
    private FileStatus status;
    
	public SPEDFile(Path path, FileType fileType, FileStatus status) {
		super();
		this.path = path;
		this.fileType = fileType;
		this.status = status;
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public FileType getFileType() {
		return fileType;
	}

	public void setFileType(FileType fileType) {
		this.fileType = fileType;
	}

	public FileStatus getStatus() {
		return status;
	}

	public void setStatus(FileStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return fileType.toString();
	}
	
	
    
    
}
