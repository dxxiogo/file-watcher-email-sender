package models.entities;

import java.time.LocalDateTime;

public class Send {
	
	private Integer id;
	private LocalDateTime date;
	private String recipient;
	private String fileName;
	private String response;
	
	public Send () {
		
	}
	
	public Send(LocalDateTime date, String recipient, String fileName, String response) {
		this.date = date;
		this.recipient = recipient;
		this.fileName = fileName;
		this.response = response;
	}
	

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
	
	
}
