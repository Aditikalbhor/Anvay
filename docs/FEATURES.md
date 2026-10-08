# ANVAY — Features

ANVAY is a Smart Indoor Navigation & Emergency Guidance System developed for educational buildings.

The application provides students, visitors, and staff with a centralized mobile platform for finding locations, navigating the building, accessing emergency guidance, and viewing important notices.

---

## 🧭 1. Indoor Navigation

ANVAY provides indoor navigation within the supported areas of the Computer/IT Building.

### Features

- Search for locations inside the building.
- Select a destination.
- View location information.
- Navigate toward the selected destination.
- Support navigation across multiple floors.
- Handle route changes when a particular route becomes unavailable.

### Supported Floors

- Ground Floor
- First Floor
- Second Floor

---

## 🔎 2. Location Search

Users can search for rooms and facilities within the building.

### Features

- Location search
- Destination selection
- Location details
- Organized building directory
- Easy access to navigation

### Basic Flow

```text
Search
  ↓
Select Location
  ↓
View Details
  ↓
Start Navigation
```

---

## 🏢 3. Building Directory

The Building Directory provides a structured view of locations available inside the supported building.

Users can browse available locations and select a destination for further information or navigation.

---

## 🚨 4. Emergency Guidance

ANVAY includes a dedicated Emergency Guidance module.

### Features

- Access emergency guidance from the application.
- One-touch emergency hotlines (Campus Security, Medical/First Aid, Fire, Department Desk, Ambulance 108).
- Display building safety and evacuation instructions.
- Provide building navigation guidance via verified staircases (S1, S2, S3) across Ground, 1st, and 2nd floors.
- Support route changes when a particular path becomes unavailable.

### Emergency Flow

```text
Emergency Activated
        ↓
Display Emergency Instructions
        ↓
Show Emergency Contacts
        ↓
Allow One-Touch Calling
        ↓
Provide Available Building Navigation Guidance
        ↓
Guide User Through Verified Building Paths / Staircases
```

---

## 🔔 5. Alerts & Notices

The application provides an Alerts & Notices section for important building-related information.

### Examples

- Important announcements
- Building alerts
- Safety information
- Other notices

Users can access these alerts directly from the application.

---

## 👤 6. User Authentication

ANVAY provides authentication and user access functionality.

### Features

- Login
- Sign Up
- Guest access
- User profile
- Local session management

The application maintains local user-session and preference information to support the application experience.

---

## 🏠 7. Home Dashboard

The Home Dashboard acts as the central entry point to the application's major functionality.

Users can access:

- Building Directory
- Search
- Emergency Guidance
- Alerts & Notices
- Profile
- Other available application modules

---

## 👤 8. User Profile

The Profile section provides access to user-related information.

Users can view their profile information from within the application.

---

## 🗓️ 9. Timetable

ANVAY includes a timetable module for displaying scheduled information.

The module provides users with access to timetable-related information through the application interface.

---

## 🔐 10. Admin Module

ANVAY includes administrative functionality for managing application information.

### Admin Features

- Manage Rooms
- Manage Routes
- Manage Alerts
- Manage Emergency Information

Administrative functionality is separated from the standard user experience.

---

## 🛣️ 11. Dynamic Route Handling

ANVAY supports route changes when a particular route becomes unavailable.

### Concept

```text
Current Route
     ↓
Route Unavailable
     ↓
Identify Alternative Route
     ↓
Update Guidance
     ↓
Continue Navigation
```

This allows the navigation experience to adapt to route availability.

---

## 📱 12. Android Application Interface

The application provides dedicated screens for different functions of the system.

Major screens include:

- Startup
- Login
- Sign Up
- Home Dashboard
- Building Directory
- Search
- Location Details
- Emergency Guidance
- Alerts & Notices
- Timetable
- Profile
- Admin Dashboard

---

## 🧩 13. Modular Application Architecture

The application is organized into separate components for better maintainability.

### Main Components

```text
UI
│
├── Activities
├── Fragments
├── Adapters
└── Dialogs

Data
│
├── Repositories
├── Authentication
├── Preferences
└── Application Data

Models
│
├── Locations
├── Alerts
├── Emergency Information
├── Timetable
└── User Profile
```

---

## 🏫 14. Educational Building Focus

The current implementation is designed around the:

**Computer/IT Building  
Government Polytechnic Pune**

The architecture allows the project to be extended to additional buildings and floors in the future.

---

## 🔮 15. Planned Future Features

Potential future enhancements include:

- AR-based indoor navigation
- Real-time location tracking
- Additional buildings and floors
- Advanced emergency routing
- Accessibility-focused navigation
- Real-time building alerts
- Additional campus facilities

---

## 📌 Feature Summary

| Feature | Description |
|---------|-------------|
| Indoor Navigation | Navigate within supported building areas |
| Location Search | Find rooms and facilities |
| Building Directory | Browse available building locations |
| Emergency Guidance | Access emergency navigation and safety information |
| Alerts & Notices | View important building information |
| Authentication | Login, Sign Up and Guest access |
| User Profile | Access user information |
| Timetable | View scheduled information |
| Admin Module | Manage rooms, routes, alerts and emergency information |
| Dynamic Routes | Handle unavailable routes |
| Multi-Floor Support | Navigate supported floors |
