# Android To-Do App

This repository is a fully functional Android To-Do application that allows users to manage daily tasks effectively. The project demonstrates proficiency in Android development, UI/UX design, and basic deployment practices.

## Features

- **Add, Edit, and Delete Tasks:** Easily create new to-do items, update existing tasks, or remove them when complete.
- **Task List View:** All tasks are displayed in a scrollable list for easy overview and management.
- **Task Completion:** Mark tasks as completed to help track progress.
- **Persistent Storage:** Tasks are saved using local storage (such as SQLite or Room database), ensuring data is retained between app launches.
- **User-Friendly Interface:** Clean and intuitive UI designed for a seamless user experience.
- **Due Dates & Priorities:** (If implemented) Set deadlines and priorities for each task to organize workload efficiently.
- **Notifications:** (If implemented) Receive reminders for tasks with approaching deadlines.

## Getting Started

### Prerequisites

- Android Studio (latest version recommended)
- Android device or emulator running Android 7.0 (API level 24) or higher

### Building and Running the App

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/r4yfon/todo-droid.git
   ```
2. **Open in Android Studio:**

- Open Android Studio.
- Choose "Open an existing project" and select the cloned directory.

3. **Build the Project:**

- Let Gradle sync the project and download dependencies.
- If prompted, update any outdated SDKs or plugins.

4. **Run the App:**

- Connect your Android device via USB (enable Developer Mode), or use an emulator.
- Click the "Run" button or use Shift + F10.

### Deployment Instructions

- The app is ready to be run on any Android device or emulator as described above.
- For publishing the app on the Google Play Store, update the app's version and package details as required and follow the Play Store deployment guide.
- To generate a signed APK or AAB for release:
  - In Android Studio, go to Build > Generate Signed Bundle/APK and follow the wizard to create a release build.
