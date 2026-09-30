ANVAY — Project Documentation

1. Project Overview

ANVAY is an Android-based Smart Indoor Navigation & Emergency Guidance System designed for navigating complex educational buildings.

The application focuses on the Computer/IT Building of Government Polytechnic Pune and provides users with a centralized platform to discover rooms and facilities, navigate through different floors, access location information, view alerts and notices, and obtain emergency guidance.

2. Problem Statement

Large educational buildings can contain multiple floors, classrooms, laboratories, offices, and other facilities, making it difficult for students, visitors, and staff to locate specific destinations.

During emergencies, finding an appropriate exit or safety location can become even more challenging.

ANVAY addresses these challenges by providing a mobile-based indoor navigation and emergency guidance solution for the building.

3. Objectives

The main objectives of ANVAY are:

Provide an easy-to-use indoor navigation system.

Help users find rooms and facilities within the building.

Provide interactive floor-based navigation.

Display information about available locations.

Provide emergency guidance.

Support route changes when a particular path becomes unavailable.

Display important alerts and notices.

Provide a centralized mobile interface for building navigation.

4. Project Scope

The current implementation focuses on the:

Computer/IT Building
Government Polytechnic Pune

The application covers:

Ground Floor

First Floor

Second Floor

The system is designed with the possibility of extending navigation to additional buildings and floors.

5. Major Modules

5.1 Authentication Module

The authentication module provides user access to the application.

It includes:

Login

Sign Up

User profile information

Guest access

The application maintains local session and preference information for the user experience.

5.2 Home Dashboard

The Home Dashboard provides access to the major functionalities of ANVAY.

Users can navigate to different sections such as:

Building Directory

Search

Emergency Guidance

Alerts and Notices

Profile

Other available application features

5.3 Building Directory

The Building Directory allows users to explore locations available inside the building.

Users can search for and select destinations such as rooms and facilities.

The directory provides a structured way of accessing building locations.

5.4 Search Module

The Search module allows users to find specific locations within the building.

The general navigation flow is:

Search Location
      ↓
Select Destination
      ↓
View Location Details
      ↓
Navigate to Destination

5.5 Indoor Navigation

ANVAY provides indoor navigation for the supported floors of the Computer/IT Building.

The navigation system helps users move toward their selected destination using the available building routes.

The application also supports route changes when a particular route becomes unavailable.

5.6 Emergency Guidance

The Emergency Guidance module is designed to assist users during emergency situations.

It provides guidance toward safer exits and safety-related locations.

The module is intended to make emergency navigation more accessible within the building.

5.7 Alerts & Notices

The Alerts & Notices module allows users to view important building-related information.

Examples may include:

Building notices

Important alerts

Safety-related information

Other announcements

5.8 Timetable Module

ANVAY includes a timetable section for displaying scheduled information relevant to the application.

The module provides users with access to timetable-related information through the application interface.

5.9 Admin Module

The application includes administrative functionality for managing application information.

The admin section provides interfaces for managing:

Rooms

Routes

Alerts

Emergency information

Administrative functionality is separated from the regular user experience.

6. Application Flow

The general application flow is:

                    ┌───────────────┐
                    │    ANVAY      │
                    │ Startup Page  │
                    └───────┬───────┘
                            ↓
                  ┌───────────────────┐
                  │ Login / Sign Up / │
                  │   Guest Access    │
                  └─────────┬─────────┘
                            ↓
                  ┌───────────────────┐
                  │   Home Dashboard  │
                  └─────────┬─────────┘
                            ↓
          ┌─────────────────┼─────────────────┐
          ↓                 ↓                 ↓
     Search /          Building          Emergency
     Directory          Locations         Guidance
          │                 │                 │
          └─────────────────┼─────────────────┘
                            ↓
                   Location / Route
                            ↓
                    Navigation

7. Technology Stack

Component

Technology

Platform

Android

Programming Language

Java

UI Development

XML

Build System

Gradle

Development Environment

Android Studio / Antigravity IDE

Version Control

Git & GitHub

8. Project Structure

The project follows a modular Android project structure.

Anvay/
│
├── app/
│   └── src/
│       └── main/
│           ├── java/
│           │   └── com/
│           │       └── gpp/
│           │           └── anvay/
│           │               ├── adapter/
│           │               ├── data/
│           │               ├── model/
│           │               └── ui/
│           │
│           ├── res/
│           │   ├── drawable/
│           │   ├── layout/
│           │   ├── menu/
│           │   ├── values/
│           │   └── xml/
│           │
│           └── AndroidManifest.xml
│
├── screenshots/
├── docs/
│
├── gradle/
│
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── gradlew
├── gradlew.bat
└── README.md

9. Package Organization

The Java source code is organized into functional packages.

adapter

Contains adapters used to connect application data with different UI components.

Examples include:

Location Adapter

Alert Adapter

Emergency Contact Adapter

Safety Instruction Adapter

Admin-related adapters

data

Contains application data and data-management classes.

Examples include:

Authentication management

Location repository

Alert repository

Emergency repository

Timetable repository

Preference management

model

Contains data models used throughout the application.

Examples include:

Location

Alert

Emergency Contact

Safety Instruction

Timetable

User Profile

ui

Contains the application's activities and user-interface components.

Examples include:

Login

Sign Up

Home

Search

Directory

Location Details

Emergency

Timetable

Profile

Alerts

10. User Experience

ANVAY is designed around a simple navigation flow so that users can reach important functionality without navigating through complicated menus.

The interface provides dedicated access to:

Location discovery

Indoor navigation

Emergency assistance

Alerts

User profile

Building information

11. Emergency Navigation Concept

Emergency situations may require users to change their planned route.

ANVAY provides emergency guidance and supports route changes when a particular path is unavailable.

The intended concept is:

Emergency Detected
        ↓
Identify Available Route
        ↓
Check Route Availability
        ↓
Guide User Toward Safer Exit
        ↓
Provide Updated Guidance

12. Setup & Installation

Prerequisites

The following are required to build and run the project:

Android Studio

JDK

Android SDK

Android device or Android emulator

Git

Clone the Repository

git clone https://github.com/Aditikalbhor/Anvay.git

Navigate into the project:

cd Anvay

Open the Project

Open Android Studio.

Select Open.

Select the cloned ANVAY project.

Allow Gradle to synchronize.

Connect an Android device or start an emulator.

Build and run the application.

13. Testing Environment

The application can be tested using:

Android Emulator

Physical Android Device

Before running the application, ensure that the required Android SDK and JDK versions are configured correctly.

14. Screenshots

Application screenshots are available in the repository's screenshots directory.

The screenshots demonstrate major application screens including:

Startup

Login

Home Dashboard

Building Directory

Emergency Guidance

Alerts & Notices

Profile

Refer to the main README for the visual application showcase.

15. Future Scope

The project can be extended with additional functionality such as:

AR-based indoor navigation

Support for additional buildings and floors

Real-time location tracking

Advanced emergency routing

Accessibility-focused navigation

Real-time building alerts

Additional campus facilities

16. Project Outcome

ANVAY demonstrates the development of an Android-based indoor navigation and emergency guidance application for an educational environment.

The project combines:

Mobile application development

Indoor navigation concepts

Building information management

Emergency guidance

User authentication

Administrative management

Location-based information presentation

17. Developer

Aditi Kalbhor

Computer Engineering Student

GitHub:

https://github.com/Aditikalbhor

18. Repository

GitHub Repository:

https://github.com/Aditikalbhor/Anvay
