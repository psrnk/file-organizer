# Automated File Organizer for the Downloads Folder

## Description
This Java application monitors the Downloads folder in real time and automatically sorts newly added files into subfolders based on their file extensions. The application uses the Java WatchService API to watch for changes in the folder and categorizes files into the following groups:
- **Images:** jpg, jpeg, png, gif
- **Documents:** pdf, doc, docx, txt
- **Videos:** mp4, avi, mkv
- **Music:** mp3, wav, flac
- **Archives:** zip, rar, 7z
- **Programs:** exe, msi, dmg
- **Others:** Files that do not match any of the above categories

## Requirements
- Java 8 or higher

## Installation & Running

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/psrnk/AutomatedFileOrganizer.git
