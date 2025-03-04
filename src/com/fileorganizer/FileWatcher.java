package com.fileorganizer;

import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;

/**
 * Monitors the Downloads folder using the WatchService API.
 */
public class FileWatcher implements Runnable {

    private Path downloadsPath;
    private FileHandler fileHandler;

    public FileWatcher(Path downloadsPath, FileHandler fileHandler) {
        this.downloadsPath = downloadsPath;
        this.fileHandler = fileHandler;
    }

    @Override
    public void run() {
        try (WatchService watchService = downloadsPath.getFileSystem().newWatchService()) {
            // Register the directory for create events
            downloadsPath.register(watchService, ENTRY_CREATE);

            System.out.println("Monitoring folder: " + downloadsPath);

            // Infinite loop to watch for events
            while (true) {
                WatchKey key = watchService.take(); // blocking call
                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();

                    // Only process newly created files
                    if (kind == ENTRY_CREATE) {
                        @SuppressWarnings("unchecked")
                        WatchEvent<Path> ev = (WatchEvent<Path>) event;
                        Path fileName = ev.context();
                        Path fullPath = downloadsPath.resolve(fileName);

                        // Process the new file
                        System.out.println("New file detected: " + fullPath);
                        fileHandler.processFile(fullPath);
                    }
                }
                // Reset the key and exit if directory is no longer accessible
                boolean valid = key.reset();
                if (!valid) {
                    System.err.println("WatchKey no longer valid.");
                    break;
                }
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error in file watcher: " + e.getMessage());
        }
    }
}
