package com.fileorganizer;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Handles file categorization and moving files to corresponding subfolders.
 */
public class FileHandler {

    private Path downloadsPath;

    public FileHandler(Path downloadsPath) {
        this.downloadsPath = downloadsPath;
    }

    /**
     * Processes the newly created file by ensuring it is stable,
     * categorizing it, and then moving it to the corresponding subfolder.
     *
     * @param file The file path to process.
     */
    public void processFile(Path file) {
        if (!Files.isRegularFile(file)) {
            return;
        }

        if (!waitForFileStability(file)) {
            System.err.println("File " + file + " is not stable. Skipping processing.");
            return;
        }

        String fileName = file.getFileName().toString();
        String extension = getFileExtension(fileName);

        FileCategory category = FileCategory.getCategoryForExtension(extension);
        String categoryFolderName = category.name().toLowerCase();

        Path targetDir = downloadsPath.resolve(categoryFolderName);
        try {
            if (!Files.exists(targetDir)) {
                Files.createDirectory(targetDir);
                System.out.println("Created directory: " + targetDir);
            }
        } catch (IOException e) {
            System.err.println("Error creating directory " + targetDir + ": " + e.getMessage());
            return;
        }

        Path targetFile = targetDir.resolve(file.getFileName());
        targetFile = resolveCollision(targetFile);

        try {
            Files.move(file, targetFile);
            System.out.println("Moved file " + file + " to " + targetFile);
        } catch (IOException e) {
            System.err.println("Error moving file " + file + " to " + targetFile + ": " + e.getMessage());
        }
    }

    /**
     * Waits until the file size is stable over several intervals, indicating the file is fully written.
     *
     * @param file The file to check.
     * @return true if the file is stable, false otherwise.
     */
    private boolean waitForFileStability(Path file) {
        long previousSize = -1;
        int stableCount = 0;
        final int requiredStableCount = 3; // number of checks to confirm stability
        final int delayMs = 1000;          // delay in milliseconds between checks
        final int maxAttempts = 10;        // maximum number of attempts before giving up
        int attempts = 0;

        while (attempts < maxAttempts) {
            try {
                long currentSize = Files.size(file);
                if (currentSize == previousSize) {
                    stableCount++;
                    if (stableCount >= requiredStableCount) {
                        return true;
                    }
                } else {
                    stableCount = 0;
                }
                previousSize = currentSize;
                Thread.sleep(delayMs);
            } catch (IOException | InterruptedException e) {
                System.err.println("Error checking file stability for " + file + ": " + e.getMessage());
                return false;
            }
            attempts++;
        }
        return false;
    }

    /**
     * Resolves naming collisions by appending a timestamp to the filename if needed.
     *
     * @param targetFile The intended target file path.
     * @return A new target file path that does not conflict with existing files.
     */
    private Path resolveCollision(Path targetFile) {
        while (Files.exists(targetFile)) {
            String fileName = targetFile.getFileName().toString();
            String baseName = fileName;
            String extension = "";
            int dotIndex = fileName.lastIndexOf(".");
            if (dotIndex != -1) {
                baseName = fileName.substring(0, dotIndex);
                extension = fileName.substring(dotIndex);
            }
            // Append current timestamp to the filename
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            String newFileName = baseName + "_" + timestamp + extension;
            targetFile = targetFile.getParent().resolve(newFileName);
        }
        return targetFile;
    }

    /**
     * Extracts the file extension from the filename.
     *
     * @param fileName The name of the file.
     * @return The extension without the dot, in lowercase. Returns an empty string if none found.
     */
    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex != -1 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }
}
