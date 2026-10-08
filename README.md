# ANVAY — Smart Indoor Navigation & Emergency Guidance System

ANVAY is an Android-based smart indoor navigation and emergency guidance system designed to help users navigate complex educational buildings efficiently and safely.

## 📌 Overview

Finding classrooms, laboratories, offices, and other facilities inside a large educational building can be difficult, especially for new students, visitors, and staff. ANVAY provides a centralized mobile application for discovering locations, navigating between floors, accessing building information, receiving alerts, and obtaining emergency guidance.

## 🎯 Objective

The objective of ANVAY is to provide a simple and accessible mobile solution for:

- Finding rooms and facilities inside the building
- Navigating between different floors and locations
- Providing building and location information
- Guiding users during emergency situations
- Delivering important building alerts and notices
- Supporting efficient access to campus facilities

## 🚀 Features

* 🔐 User authentication
* 🔎 Search for rooms and locations
* 🏢 Multi-floor building directory & topology
* 📍 Indoor navigation
* 🧭 Dynamic route guidance
* 🚨 Emergency guidance
* 🔄 Route recalculation when a path is blocked
* 📱 Interactive building navigation

## 🛠️ Tech Stack

* **Language:** Java
* **Platform:** Android
* **UI:** XML
* **IDE:** Android Studio / Antigravity IDE
* **Build System:** Gradle

## 🏫 Project Scope

The current implementation focuses on the **Computer/IT Building of Government Polytechnic Pune**, covering:

* Ground Floor
* First Floor
* Second Floor

## 📱 Application Flow

```text
Login / Guest Access
        ↓
Home Dashboard / Search / Directory
        ↓
Select Location
        ↓
Location Details
        ↓
Navigate
        ↓
Normal NavigationActivity
        ↓
Optional AR Navigation
```

## 🚨 Emergency Guidance

ANVAY provides emergency guidance and safety instructions during emergencies, offering clearly visible one-touch emergency hotlines (Campus Security, Medical/First Aid, Fire, Department Desk, Ambulance 108) and guiding users through verified building staircases and corridors across Ground, 1st, and 2nd floors.

## 📂 Project Structure

```text
ANVAY/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       ├── res/
│   │       └── AndroidManifest.xml
│   └── build.gradle
├── gradle/
├── build.gradle
├── settings.gradle
└── README.md
```

## ⚙️ Setup

### Prerequisites

* Android Studio
* JDK
* Android SDK
* Android device or emulator

### Run the Project

1. Clone the repository.
2. Open the project in Android Studio.
3. Allow Gradle to sync.
4. Connect an Android device or start an emulator.
5. Build and run the application.

## 📱 Screenshots

### 🚀 Startup Screen

![ANVAY Startup](screenshots/Anvay_Startup_Page.jpeg)

### 🔐 Login

![ANVAY Login](screenshots/Anvay_Login_Page.jpeg)

### 🏠 Home Dashboard

![ANVAY Home](screenshots/Anvay_Home_Page.jpeg)

### 📚 Building Directory

![ANVAY Building Directory](screenshots/Anvay_Building_Directory.jpeg)

### 🚨 Emergency Guidance

![ANVAY Emergency Guidance](screenshots/Anvay_Emergency_Guidance.jpeg)

### 🔔 Alerts & Notices

![ANVAY Alerts](screenshots/Anvay_Alert_&_Notice.jpeg)

### 👤 Profile

![ANVAY Profile](screenshots/Anvay_Profile_Page.jpeg)

## 🔮 Future Scope

* AR-based indoor navigation
* More buildings and floors
* Real-time location tracking
* Advanced emergency routing
* Accessibility-focused navigation
* Real-time building alerts

## 👩‍💻 Developer

**Aditi Kalbhor**

Computer Engineering Student
