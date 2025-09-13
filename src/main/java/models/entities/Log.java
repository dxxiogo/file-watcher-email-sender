

package models.entities;

import java.time.LocalDateTime;

import models.entities.enums.LogStatus;

public class Log {
	
	private LocalDateTime date;
	private String description;
	private LogStatus status;
	
	
	public Log(LocalDateTime date, String description, LogStatus status) {
		this.date = date;
		this.description = description;
		this.setStatus(status);
	}


	public LocalDateTime getDate() {
		return date;
	}


	public void setDate(LocalDateTime date) {
		this.date = date;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public LogStatus getStatus() {
		return status;
	}


	public void setStatus(LogStatus status) {
		this.status = status;
	}

	
	
}
