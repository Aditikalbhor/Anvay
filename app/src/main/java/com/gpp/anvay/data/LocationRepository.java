package com.gpp.anvay.data;

import com.gpp.anvay.model.LocationItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocationRepository {
    private static LocationRepository instance;
    private final List<LocationItem> locations;

    private LocationRepository() {
        locations = new ArrayList<>(MockDataProvider.getInitialLocations());
    }

    public static synchronized LocationRepository getInstance() {
        if (instance == null) {
            instance = new LocationRepository();
        }
        return instance;
    }

    public List<LocationItem> getAllLocations() {
        return new ArrayList<>(locations);
    }

    public LocationItem getLocationById(String id) {
        if (id == null) return null;
        for (LocationItem item : locations) {
            if (id.equals(item.getId())) {
                return item;
            }
        }
        return null;
    }

    public List<LocationItem> searchLocations(String query, String categoryFilter, String floorFilter) {
        List<LocationItem> result = new ArrayList<>();
        String q = query != null ? query.trim().toLowerCase(Locale.ROOT) : "";

        for (LocationItem item : locations) {
            boolean matchesCategory = categoryFilter == null || categoryFilter.equalsIgnoreCase("All") ||
                    (item.getCategory() != null && item.getCategory().equalsIgnoreCase(categoryFilter));

            boolean matchesFloor = floorFilter == null || floorFilter.equalsIgnoreCase("All") || floorFilter.equalsIgnoreCase("All Floors") ||
                    (item.getFloor() != null && item.getFloor().equalsIgnoreCase(floorFilter));

            if (!matchesCategory || !matchesFloor) {
                continue;
            }

            if (q.isEmpty()) {
                result.add(item);
                continue;
            }

            // Search multi-criteria
            boolean matchesName = item.getName() != null && item.getName().toLowerCase(Locale.ROOT).contains(q);
            boolean matchesRoom = item.getRoomNumber() != null && item.getRoomNumber().toLowerCase(Locale.ROOT).contains(q);
            boolean matchesDept = item.getDepartment() != null && item.getDepartment().toLowerCase(Locale.ROOT).contains(q);
            boolean matchesDesc = item.getDescription() != null && item.getDescription().toLowerCase(Locale.ROOT).contains(q);
            boolean matchesInCharge = item.getInCharge() != null && item.getInCharge().toLowerCase(Locale.ROOT).contains(q);

            boolean matchesFacility = false;
            if (item.getFacilities() != null) {
                for (String f : item.getFacilities()) {
                    if (f.toLowerCase(Locale.ROOT).contains(q)) {
                        matchesFacility = true;
                        break;
                    }
                }
            }

            if (matchesName || matchesRoom || matchesDept || matchesDesc || matchesInCharge || matchesFacility) {
                result.add(item);
            }
        }
        return result;
    }

    public List<LocationItem> getLocationsByCategory(String category) {
        if (category == null || category.equalsIgnoreCase("All")) {
            return getAllLocations();
        }
        List<LocationItem> list = new ArrayList<>();
        for (LocationItem item : locations) {
            if (category.equalsIgnoreCase(item.getCategory())) {
                list.add(item);
            }
        }
        return list;
    }

    public List<LocationItem> getLocationsByFloor(String floor) {
        if (floor == null || floor.equalsIgnoreCase("All") || floor.equalsIgnoreCase("All Floors")) {
            return getAllLocations();
        }
        List<LocationItem> list = new ArrayList<>();
        for (LocationItem item : locations) {
            if (floor.equalsIgnoreCase(item.getFloor())) {
                list.add(item);
            }
        }
        return list;
    }

    public void addLocation(LocationItem item) {
        if (item != null) {
            locations.add(0, item);
        }
    }

    public boolean updateLocation(LocationItem updated) {
        if (updated == null) return false;
        for (int i = 0; i < locations.size(); i++) {
            if (locations.get(i).getId().equals(updated.getId())) {
                locations.set(i, updated);
                return true;
            }
        }
        return false;
    }

    public boolean deleteLocation(String id) {
        if (id == null) return false;
        for (int i = 0; i < locations.size(); i++) {
            if (id.equals(locations.get(i).getId())) {
                locations.remove(i);
                return true;
            }
        }
        return false;
    }

    public void toggleBookmark(String id) {
        LocationItem item = getLocationById(id);
        if (item != null) {
            item.setBookmarked(!item.isBookmarked());
        }
    }

    public List<LocationItem> getBookmarkedLocations() {
        List<LocationItem> list = new ArrayList<>();
        for (LocationItem item : locations) {
            if (item.isBookmarked()) {
                list.add(item);
            }
        }
        return list;
    }
}
