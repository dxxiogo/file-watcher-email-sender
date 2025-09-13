package models.services;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import models.entities.Client;
import models.interfaces.LogService;
import models.interfaces.MailService;
import utils.HandleFile;

public class WacthFolderService {

	private final WatchService watchService;
	private final Map<WatchKey, Path> keyPathMap = new HashMap<>();
	private final MailService mailService;
	private final LogService logService;

	public WacthFolderService(Path rootDir, MailService mailService) throws IOException {
		this.watchService = FileSystems.getDefault().newWatchService();
		this.mailService = mailService;
		this.logService = new LogFileService();

		registerAll(rootDir);
	}

	private void registerAll(final Path start) throws IOException {
		Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				register(dir);
				return FileVisitResult.CONTINUE;
			}
		});
	}

	private void register(Path dir) throws IOException {
		WatchKey key = dir.register(watchService, ENTRY_CREATE, ENTRY_DELETE);
		keyPathMap.put(key, dir);
	}

	public void processEvents() throws IOException {
		while (true) {
			WatchKey key;
			try {
				key = watchService.take();
			} catch (InterruptedException ex) {
				return;
			}

			Path dir = keyPathMap.get(key);
			if (dir == null)
				continue;

			for (WatchEvent<?> event : key.pollEvents()) {
				WatchEvent.Kind<?> kind = event.kind();
				Path name = (Path) event.context();
				Path child = dir.resolve(name);

				if (kind == ENTRY_CREATE) {
					if (Files.isDirectory(child)) {
						System.out.println("A directory was created: " + child);
						registerAll(child);
					} else if (Files.isRegularFile(child)) {
						System.out.println("A file was created: " + child);
						if (child.toString().toUpperCase().contains("VALIDADO")
								|| child.toString().toUpperCase().contains("RETIFICADO")) {
							File parentFolder = new File(child.getParent().toString());
							List<Path> pathFiles = new ArrayList<>();
							List<File> listFiles = Arrays.asList(parentFolder.listFiles());
							List<File> filesFiltered = listFiles.stream()
									.filter(file -> (file.getName().toUpperCase().contains("SPED")
											&& file.getName().contains("VALIDADO"))
											|| (file.getName().toUpperCase().contains("CONTRIBUICOES")
													&& file.getName().contains ("VALIDADO")))
									.collect(Collectors.toList());

							if (filesFiltered.size() > 1) {
								String filesNameConcateneted = "";

								for (File file : filesFiltered) {
									filesNameConcateneted += file.getName();
									pathFiles.add(file.toPath());
								}

								if (filesNameConcateneted.toUpperCase().contains("CONTRIBUICOES")
										&& filesNameConcateneted.toUpperCase().contains("SPED")) {
									Client client = HandleFile.extractClientInfo(pathFiles);
									System.out.println("Extracted info: " + client);
									this.mailService.sendEmail(client);
								}

							} else {

								boolean hasContribuicoes = listFiles.stream()
										.anyMatch(file -> file.getName().toUpperCase().contains("CONTRIBUICOES"));
								if (!hasContribuicoes) {
									pathFiles.add(child);
									Client client = HandleFile.extractClientInfo(pathFiles);
									System.out.println("Extracted info: " + client);
									this.mailService.sendEmail(client);
								}
							}
						}
					}
				}
			}

			boolean valid = key.reset();
			if (!valid) {
				keyPathMap.remove(key);
				if (keyPathMap.isEmpty())
					break;
			}
		}
	}

}
