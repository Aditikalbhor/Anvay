package com.gpp.anvay.position;

import com.gpp.anvay.model.position.UserPosition;

/**
 * Deterministic mock position provider designed specifically for unit tests and architecture validation.
 * Does not use real hardware or emit fake coordinates automatically; allows controlled positions to be
 * injected and dispatched to verified listeners.
 */
public class MockIndoorPositionProvider implements IndoorPositionProvider {

    private PositionUpdateListener listener;
    private UserPosition lastKnownPosition;
    private boolean isTracking = false;

    @Override
    public void startTracking() {
        this.isTracking = true;
    }

    @Override
    public void stopTracking() {
        this.isTracking = false;
    }

    @Override
    public boolean isTracking() {
        return isTracking;
    }

    @Override
    public UserPosition getLastKnownPosition() {
        return lastKnownPosition;
    }

    @Override
    public void setPositionUpdateListener(PositionUpdateListener listener) {
        this.listener = listener;
    }

    @Override
    public String getProviderName() {
        return "MockIndoorPositionProvider";
    }

    /**
     * Manually emits a test position to the active listener and updates the last known position.
     * Useful for deterministic testing of the positioning pipeline.
     */
    public void emitPosition(UserPosition position) {
        this.lastKnownPosition = position;
        if (listener != null) {
            listener.onPositionUpdated(position);
        }
    }

    /**
     * Clears the current mock position state.
     */
    public void clearPosition() {
        this.lastKnownPosition = null;
    }
}
