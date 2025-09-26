package models.services;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import models.entities.Client;
import models.interfaces.MailService;
import utils.HandleFile;
import utils.LoadProperties;

public class HandleFileService {
	
	private final MailService mailService;
	
	
	public HandleFileService(MailService mailService) {
		this.mailService = mailService;
	}
	
	
	public void handleFile (Path pathFile) {
		
		Path infoFile = pathFile.getParent().getParent().resolve("paramenters.properties");
		System.out.println(infoFile);
		Properties props = LoadProperties.loadConfig(infoFile.toString());

		String types = props.getProperty("FilesType");
		List<String> fileTypes = Arrays.asList(types.split(","));
		
		String sufixesProps = props.getProperty("Sufixes");
		List<String> sufixes = Arrays.asList(sufixesProps.split(","));

		boolean sendAllTogether = Boolean.parseBoolean(props.getProperty("SendAllTogether"));
		
	
		boolean isTypeIncluded = false;
		boolean isSufixeIncluded = false;
		StringBuilder sufixeIncluded = new StringBuilder();
		
		for (String typeFile : fileTypes) {
			
			if(pathFile.getFileName().toString().toUpperCase().contains(typeFile)) {
				isTypeIncluded = true;
			}
		}
		
		for (String sufixe: sufixes) {
			if(pathFile.getFileName().toString().toUpperCase().contains(sufixe)) {
				isSufixeIncluded = true;
				sufixeIncluded.append(sufixe);
			}
		}
		
		
		if (Files.isRegularFile(pathFile) && isTypeIncluded && isSufixeIncluded) {
			List<Path> pathFiles = new ArrayList<>();
			
			if(sendAllTogether) {
				File parentFolder = new File(pathFile.getParent().toString());
				List<File> listFiles = Arrays.asList(parentFolder.listFiles());
				List<File> listFilteredFiles =
						listFiles.stream().filter(file -> (file.getName().toUpperCase().contains(sufixeIncluded)))
						.collect(Collectors.toList());
				
				if(listFilteredFiles.size() == fileTypes.size()) {
					Map<String, String> files = new HashMap<>();
					for(int i = 0; i < fileTypes.size(); i++) {
						for(int k = 0; k < listFilteredFiles.size(); k++) {
							if(listFilteredFiles.get(k).toString().toUpperCase().contains(fileTypes.get(i))) {
								files.put(fileTypes.get(i), listFilteredFiles.get(k).getName());
								pathFiles.add(listFilteredFiles.get(k).toPath());
							}
						}
					}
					
					if(files.size() == fileTypes.size()) {
						Client client = HandleFile.extractClientInfo(pathFiles, sufixeIncluded.toString());
						System.out.println("Extracted info: " + client);
						this.mailService.sendEmail(client);
					} 
				} else {
					System.out.println("Falta alguns arquivos");
				}
				
				
			} else {
				pathFiles.add(pathFile);
				Client client = HandleFile.extractClientInfo(pathFiles, sufixeIncluded.toString());
				System.out.println("Extracted info: " + client);
				this.mailService.sendEmail(client);
			}
			
		}
	}
	
}
