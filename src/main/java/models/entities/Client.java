package models.entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class Client {

    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM/yyyy");

    private String name;
    private String[] emails;
    private LocalDate date;
    private List<SPEDFile> files;

    
	public Client(String name, String[] emails, LocalDate date, List<SPEDFile> files) {
		this.name = name;
		this.emails = emails;
		this.date = date;
		this.files = files;
	}
	
	
	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getEmails() {
        return emails;
    }

    public void setEmails(String[] email) {
        this.emails = email;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

 
    public List<SPEDFile> getFiles() {
		return files;
	}


	public void setFiles(List<SPEDFile> files) {
		this.files = files;
	}



	@Override
    public String toString() {
        return String.format("%s %s", this.name, date.format(fmt));
    }
}
