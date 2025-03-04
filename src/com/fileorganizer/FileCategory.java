package com.fileorganizer;

import java.util.Arrays;
import java.util.List;

/**
 * Enum representing file categories.
 * Each category holds a list of associated file extensions.
 */
public enum FileCategory {
    IMAGES(Arrays.asList("jpg", "jpeg", "png", "gif")),
    DOCUMENTS(Arrays.asList("pdf", "doc", "docx", "txt")),
    VIDEOS(Arrays.asList("mp4", "avi", "mkv")),
    MUSIC(Arrays.asList("mp3", "wav", "flac")),
    ARCHIVES(Arrays.asList("zip", "rar", "7z")),
    PROGRAMS(Arrays.asList("exe", "msi", "dmg")),
    OTHERS(Arrays.asList());

    private List<String> extensions;

    FileCategory(List<String> extensions) {
        this.extensions = extensions;
    }

    /**
     * Get the list of extensions associated with the category.
     *
     * @return List of extensions.
     */
    public List<String> getExtensions() {
        return extensions;
    }

    /**
     * Determines the file category based on the file extension.
     *
     * @param extension The file extension (without dot), in lowercase.
     * @return FileCategory corresponding to the extension.
     */
    public static FileCategory getCategoryForExtension(String extension) {
        for (FileCategory category : FileCategory.values()) {
            if (category != OTHERS && category.getExtensions().contains(extension.toLowerCase())) {
                return category;
            }
        }
        return OTHERS;
    }
}