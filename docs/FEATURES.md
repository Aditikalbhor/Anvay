# ANVAY — Features

ANVAY is a Smart Indoor Navigation & Emergency Guidance System developed for educational buildings.

The application provides students, visitors, and staff with a centralized mobile platform for finding locations, navigating the building via photo-grounded visual guidance and AR, accessing emergency guidance, and viewing important notices.

---

## 🧭 1. Indoor Navigation & Photo-Grounded Visual Guidance

ANVAY provides indoor navigation within the supported areas of the Computer/IT Building.

### Core Features

- Search for locations inside the building.
- Select a destination.
- View detailed location information (In-charge, operating hours, facilities).
- Standard turn-by-turn navigation route guidance.
- **Photo-Grounded Visual Navigation:** Real photographs captured during a physical building walkthrough serve as visual landmarks for corridors, junctions, room entrances, and staircase landings.
- **Multi-Floor Staircase Transitions:** Clear visual guidance and alerts when ascending or descending staircases (S1, S2, S3).
- **Augmented Reality (AR) Overlay:** Live camera feed direction overlays utilizing the identical underlying `NavigationRoute`.
- **Building Level Indicator:** 3D-style floor perspective visualizer indicating active and target floors.
- **Dynamic Route Recalculation:** Automatic recalculation using BFS pathfinding when paths are modified.

### Supported Floors

- Ground Floor
- 1st Floor
- 2nd Floor

*(Note: The Computer/IT Building has strictly 3 floors. No 3rd floor exists).*

---

## 📸 2. Photo-Grounded Visual Navigation Architecture

ANVAY implements a dedicated visual navigation pipeline:

```text
Building (Computer/IT Building)
  ↓
Floor (Ground / 1st / 2nd)
  ↓
NavigationNode (ROOM_ENTRY, CORRIDOR_JUNCTION, STAIRCASE, CHECKPOINT)
  ↓
NavigationGraph (Single Source of Truth)
  ↓
PathFinder (BFS Algorithm)
  ↓
NavigationRoute
  ↓
VisualNavigationManager
  ├── VisualNavigationRepository (80 verified local photos)
  ├── VisualNavigationStep (Floor transitions, ARDirection, visual landmarks)
  └── VisualNavigationActivity (Photo display, floor indicators, step controls)
```

### Visual Landmark Types
- `ROOM_ENTRANCE`: Verified entrance door views (e.g. IT Labs 1–4, CR14–CR22, Staff Rooms).
- `CORRIDOR_VIEW`: Perspective corridor pathways.
- `STAIRCASE_VIEW`: Approach views to stairwells S1, S2, S3.
- `STAIRCASE_LANDING`: Mid-level and floor-level landing views.
- `JUNCTION_VIEW`: Main wing corridor intersections.
- `FLOOR_TRANSITION`: Ascending / descending stairwell transitions.
- `DESTINATION_VIEW`: Arrival confirmation view.

---

## 🔎 3. Location Search & Directory

Users can search for rooms and facilities within the building.

### Features

- Search by room name, code, category, or floor
- Filter by department, facilities, or wing
- Location details with nearby landmarks and facility tags
- Direct navigation trigger

### Flow

```text
Search / Directory
       ↓
Select Room
       ↓
Location Detail Screen
       ↓
Start Navigation
   ├── Visual Guidance
   └── AR Navigation
```

---

## 🚨 4. Emergency Guidance

ANVAY includes a verified Emergency Guidance module.

### Features

- Clearly visible one-touch emergency hotlines:
  - **101** (Fire Brigade)
  - **108** (Medical / Ambulance)
  - Campus Security
  - First Aid Center
  - Department Emergency Desk
- Display building safety and evacuation instructions.
- Building navigation guidance via verified staircases (S1, S2, S3) down to Ground Floor.
- Strictly no fabricated emergency exits or non-existent assembly areas.

---

## 🔔 5. Alerts & Notices

The application provides an Alerts & Notices section for important building-related announcements, schedule updates, and safety notices.

---

## 👤 6. User Authentication & Guest Access

- Login with credentials
- Student / Faculty Sign Up
- One-tap Guest Mode for instant access
- Profile management with departmental role info

---

## 🏠 7. Home Dashboard

Central hub offering quick access to:
- Building Directory & Search
- Active campus alerts banner
- Emergency Guidance
- Timetable & schedules
- Profile & settings

---

## 🗓️ 8. Timetable

Dedicated timetable viewer for class schedules and lab sessions across departments.

---

## 🔐 9. Admin Module

Administrative management tools for authorized campus staff:
- Manage Rooms & Facilities
- Manage Navigation Routes & Blockages
- Manage Building Alerts
- Manage Emergency Contacts

---

## 📌 Feature Summary

| Feature | Description |
|---------|-------------|
| Indoor Navigation | Turn-by-turn routing using BFS PathFinder |
| Photo-Grounded Guidance | Visual landmarks using 80 real building photos |
| AR Navigation | Camera overlay directions using active route |
| Staircase Floor Transitions | Vertical navigation via S1, S2, S3 |
| Location Search | Fast room search with category filters |
| Emergency Guidance | Safety advice and one-touch hotlines (101, 108, Security) |
| Building Level Visualizer | 3D-style floor perspective strip |
| Multi-Floor Support | Ground, 1st, and 2nd Floors |
| Offline Operation | 100% bundled local assets (no internet dependency) |
