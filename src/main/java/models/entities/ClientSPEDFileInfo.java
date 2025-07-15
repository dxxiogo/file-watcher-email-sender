package models.entities;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import models.entities.enums.FileStatus;

public class ClientSPEDFileInfo {

    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM/yyyy");

    private String name;
    private String email;
    private LocalDate date;
    private FileStatus status;
    private Path path;

    public ClientSPEDFileInfo(String name, String email, LocalDate date, FileStatus status, Path path) {
        this.name = name;
        this.email = email;
        this.date = date;
        this.status = status;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public FileStatus getStatus() {
        return status;
    }

    public void setStatus(FileStatus status) {
        this.status = status;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return String.format("SPED %s %s - %s", this.name, date.format(fmt), this.status.toString());
    }
}
