# ANVAY — Project Documentation

## 1. Project Overview

ANVAY is an Android-based Smart Indoor Navigation & Emergency Guidance System designed for navigating educational buildings.

The application focuses on the **Computer/IT Building of Government Polytechnic Pune** and provides users with a centralized platform to discover rooms and facilities, navigate through different floors with photo-grounded visual landmarks and AR guidance, access location details, view alerts and notices, and obtain emergency guidance.

## 2. Problem Statement

Large educational buildings contain multiple floors, classrooms, laboratories, offices, and facilities, making it difficult for students, visitors, and staff to locate destinations quickly.

Traditional 2D indoor maps often lack intuitive physical context. ANVAY solves this by pairing topological pathfinding with real photographic landmarks captured directly from the physical building, bridging the gap between digital navigation and the physical environment.

## 3. Objectives

The main objectives of ANVAY are:

1. Provide an intuitive indoor navigation system grounded in real physical photographs.
2. Maintain `NavigationGraph` as the single source of truth for routing.
3. Guide users through verified multi-floor staircase transitions (`S1`, `S2`, `S3`).
4. Support AR camera overlays powered by the same navigation route.
5. Provide verified emergency guidance with one-touch hotlines (101, 108, Campus Security, Medical).
6. Provide an interactive 3D-style floor perspective visualizer.
7. Operate 100% locally and offline without external image hosting dependencies.

## 4. Project Scope & Building Topology

### Building Scope
- **Building:** Computer/IT Building, Government Polytechnic Pune
- **Floors:** Ground Floor, 1st Floor, 2nd Floor (Strictly 3 floors; no 3rd floor).

### Staircase Identifiers & Floor Connections
- **Staircase S1 (East Wing):** `GF-S1 ↔ FF-S1 ↔ SF-S1`
- **Staircase S2 (Central Lobby):** `GF-S2 ↔ FF-S2 ↔ SF-S2`
- **Staircase S3 (West Wing):** `GF-S3 ↔ FF-S3 ↔ SF-S3`

### Node Types
The navigation topology permits only four verified node types:
- `ROOM_ENTRY`: Entrance doorway to rooms, labs, offices.
- `CORRIDOR_JUNCTION`: Corridor intersection or waypoint.
- `STAIRCASE`: Staircase landing connecting floors.
- `CHECKPOINT`: Physical positioning reference.

*(Strictly no fabricated `EXIT` or `EMERGENCY_EXIT` nodes).*

## 5. Major Modules

### 5.1 Photo-Grounded Visual Navigation Module
- **Dataset:** 80 high-resolution photos bundled locally in `drawable-nodpi/`.
- **`VisualLandmark`:** Represents verified physical landmarks with controlled types (`ROOM_ENTRANCE`, `CORRIDOR_VIEW`, `STAIRCASE_VIEW`, `STAIRCASE_LANDING`, `JUNCTION_VIEW`, `FLOOR_TRANSITION`, `DESTINATION_VIEW`).
- **`VisualNavigationRepository`:** Maps topological nodes and staircase transitions to photograph resources.
- **`VisualNavigationManager`:** Consumes `NavigationRoute` from the BFS `PathFinder`, resolves visual landmarks, determines directional cues, and tracks step progression without mutating the graph.
- **`VisualNavigationActivity`:** Presents visual navigation with real building photos, floor badges, step progress, 3D floor indicator, floor transition alerts, and manual step controls.

### 5.2 AR Navigation Module
- Utilizes `ARNavigationActivity` and `AROverlayView`.
- Consumes the exact same `NavigationRoute` as standard and visual navigation.
- Live camera preview with AR directional arrows (Forward, Left, Right, Staircase, Arrival).

### 5.3 Indoor Navigation (Topological)
- Standard turn-by-turn guidance with `NavigationActivity`.
- BFS pathfinding calculated across `NavigationGraph`.
- Multi-floor route support and dynamic recalculation.

### 5.4 Location Discovery & Building Directory
- Search rooms by name, number, department, wing, or category.
- Comprehensive room details (In-charge, operating hours, facilities, nearest staircases).

### 5.5 Emergency Guidance Module
- One-touch emergency calling:
  - **101** (Fire Brigade)
  - **108** (Ambulance / Medical)
  - Campus Security Office
  - Medical / First Aid Room
  - Department Emergency Contact
- Safety instructions and staircase descent guidance.

### 5.6 Admin & Management Module
- Manage rooms, routes, alerts, and emergency information.

## 6. Architecture & Data Flow

```text
Building
  ↓
Floor
  ↓
NavigationNode
  ↓
NavigationGraph (Single Source of Truth)
  ↓
PathFinder (BFS Algorithm)
  ↓
NavigationRoute
  ↓
NavigationManager
  ├── Normal Navigation (NavigationActivity)
  ├── Visual Navigation (VisualNavigationActivity)
  │     └── VisualNavigationManager + VisualNavigationRepository (80 Photos)
  └── AR Navigation (ARNavigationActivity)
```

## 7. Positioning & Limitations

- **Positioning Architecture:** Provider-independent (`IndoorPositionProvider`, `UserPositionManager`).
- **Academic Demo Limitation:** For testing and evaluation, navigation uses manual/mock step progression (`Next Step`, `Previous Step`, `Restart`, or selecting a starting anchor). Real-time indoor GPS, BLE beacons, Wi-Fi fingerprinting, and automated camera localization are not claimed.

## 8. Technology Stack

- **Language:** Java 17
- **Platform:** Android (API 31+ target, min API 24)
- **UI:** XML with Material Design Components
- **Build System:** Gradle

## 9. Verification & Quality Assurance

- All unit tests pass (`.\gradlew testDebugUnitTest`).
- Full Java compilation succeeds (`.\gradlew compileDebugJavaWithJavac`).
- Debug APK successfully assembled (`.\gradlew assembleDebug` -> `app-debug.apk` ~14.5 MB).
