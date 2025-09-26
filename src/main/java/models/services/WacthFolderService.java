package models.services;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
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
import java.util.HashMap;
import java.util.Map;

public class WacthFolderService {

	private final WatchService watchService;
	private final Map<WatchKey, Path> keyPathMap = new HashMap<>();
	private final HandleFileService fileService;

	public WacthFolderService(Path rootDir, HandleFileService fileService) throws IOException {
		this.watchService = FileSystems.getDefault().newWatchService();
		this.fileService = fileService;

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
					fileService.handleFile(child);
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
