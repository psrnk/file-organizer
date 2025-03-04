# Automated File Organizer for the Downloads Folder

## Description
This Java application monitors your Downloads folder in real time and automatically organizes newly added files into subfolders based on their file extensions. It uses the Java WatchService API to detect changes and sorts files into the following categories:
- **Images:** jpg, jpeg, png, gif
- **Documents:** pdf, doc, docx, txt
- **Videos:** mp4, avi, mkv
- **Music:** mp3, wav, flac
- **Archives:** zip, rar, 7z
- **Programs:** exe, msi, dmg
- **Others:** Files that do not match any of the above categories

## Features
- **Real-Time Monitoring:** Continuously watches the Downloads folder for new files.
- **Automatic Categorization:** Determines file category based on file extension.
- **Collision Handling:** Resolves naming collisions by appending a timestamp.
- **Stability Check:** Waits until a file is fully written before processing.
- **Logging & Error Handling:** Logs file movements and errors to the console.

## Requirements
- Java 8 or higher

## Installation & Running

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/yourusername/AutomatedFileOrganizer.git

2. **Navigate to Source Directory and Compile:**
   ```bash
   cd AutomatedFileOrganizer/src
   javac com/fileorganizer/*.java

3. **Run the application**
   ```bash
   java com.fileorganizer.FileOrganizer
