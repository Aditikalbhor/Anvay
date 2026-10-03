package com.gpp.anvay.position;

import com.gpp.anvay.model.position.UserPosition;

import java.util.ArrayList;
import java.util.List;

/**
 * Central manager for indoor user positioning.
 * Manages the active {@link IndoorPositionProvider}, caches the latest {@link UserPosition},
 * and dispatches updates to registered UI / navigation listeners.
 */
public class UserPositionManager {

    public interface UserPositionListener {
        void onUserPositionChanged(UserPosition position);
    }

    private static UserPositionManager instance;

    private IndoorPositionProvider activeProvider;
    private UserPosition currentPosition;
    private final List<UserPositionListener> listeners;
    private final IndoorPositionProvider.PositionUpdateListener providerCallback;

    private UserPositionManager() {
        this.listeners = new ArrayList<>();
        this.providerCallback = new IndoorPositionProvider.PositionUpdateListener() {
            @Override
            public void onPositionUpdated(UserPosition position) {
                currentPosition = position;
                notifyListeners(position);
            }
        };
    }

    public static synchronized UserPositionManager getInstance() {
        if (instance == null) {
            instance = new UserPositionManager();
        }
        return instance;
    }

    /**
     * Sets or switches the active indoor position provider.
     * Cleans up the previous provider before attaching to the new one.
     */
    public synchronized void setPositionProvider(IndoorPositionProvider provider) {
        boolean wasTracking = false;
        if (activeProvider != null) {
            wasTracking = activeProvider.isTracking();
            activeProvider.stopTracking();
            activeProvider.setPositionUpdateListener(null);
        }

        this.activeProvider = provider;

        if (activeProvider != null) {
            activeProvider.setPositionUpdateListener(providerCallback);
            if (wasTracking) {
                activeProvider.startTracking();
            }
        }
    }

    public synchronized IndoorPositionProvider getPositionProvider() {
        return activeProvider;
    }

    /**
     * Starts positioning with the active provider if configured.
     */
    public synchronized void startPositioning() {
        if (activeProvider != null && !activeProvider.isTracking()) {
            activeProvider.startTracking();
        }
    }

    /**
     * Stops active positioning.
     */
    public synchronized void stopPositioning() {
        if (activeProvider != null && activeProvider.isTracking()) {
            activeProvider.stopTracking();
        }
    }

    public synchronized boolean isPositioningActive() {
        return activeProvider != null && activeProvider.isTracking();
    }

    /**
     * Retrieves the current user position if established, or null.
     */
    public synchronized UserPosition getCurrentPosition() {
        if (currentPosition != null) {
            return currentPosition;
        }
        if (activeProvider != null) {
            return activeProvider.getLastKnownPosition();
        }
        return null;
    }

    public synchronized boolean hasValidPosition() {
        return getCurrentPosition() != null;
    }

    public synchronized String getCurrentFloor() {
        UserPosition pos = getCurrentPosition();
        return pos != null ? pos.getFloor() : null;
    }

    public synchronized String getCurrentBuildingId() {
        UserPosition pos = getCurrentPosition();
        return pos != null ? pos.getBuildingId() : null;
    }

    /**
     * Registers a listener for real-time user position changes.
     */
    public synchronized void addListener(UserPositionListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    /**
     * Unregisters a position listener.
     */
    public synchronized void removeListener(UserPositionListener listener) {
        if (listener != null) {
            listeners.remove(listener);
        }
    }

    public synchronized void clearListeners() {
        listeners.clear();
    }

    /**
     * Resets current position data and state.
     */
    public synchronized void resetPosition() {
        this.currentPosition = null;
        if (activeProvider != null) {
            activeProvider.stopTracking();
        }
    }

    private synchronized void notifyListeners(UserPosition position) {
        List<UserPositionListener> snapshot = new ArrayList<>(listeners);
        for (UserPositionListener listener : snapshot) {
            listener.onUserPositionChanged(position);
        }
    }
}
