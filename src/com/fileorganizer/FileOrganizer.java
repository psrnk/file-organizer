package com.fileorganizer;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Main class to start the Automated File Organizer.
 */
public class FileOrganizer {

    public static void main(String[] args) {
        // Define the path to the Downloads folder.
        // You may change this path if your Downloads folder is in a different location.
        String downloadsFolderPath = System.getProperty("user.home") + "/Downloads";
        Path downloadsPath = Paths.get(downloadsFolderPath);

        System.out.println("Starting Automated File Organizer for folder: " + downloadsPath);

        // Initialize the FileHandler with the Downloads folder path.
        FileHandler fileHandler = new FileHandler(downloadsPath);

        // Create and start the FileWatcher in a new thread.
        FileWatcher fileWatcher = new FileWatcher(downloadsPath, fileHandler);
        Thread watcherThread = new Thread(fileWatcher);
        watcherThread.start();

        // The main thread can be used for additional tasks if needed.
    }
}
