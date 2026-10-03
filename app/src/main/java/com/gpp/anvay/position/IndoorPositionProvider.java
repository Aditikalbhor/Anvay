package com.gpp.anvay.position;

import com.gpp.anvay.model.position.UserPosition;

/**
 * Common contract for indoor positioning providers.
 * Concrete implementations may include Mock, QR-checkpoint, BLE beacon, or Sensor-PDR providers.
 */
public interface IndoorPositionProvider {

    /**
     * Listener interface for receiving position updates emitted by the provider.
     */
    interface PositionUpdateListener {
        void onPositionUpdated(UserPosition position);
    }

    /**
     * Starts active positioning / location listening.
     */
    void startTracking();

    /**
     * Stops active positioning / location listening.
     */
    void stopTracking();

    /**
     * Returns whether tracking is currently active.
     */
    boolean isTracking();

    /**
     * Retrieves the latest known position from the provider, or null if unestablished.
     */
    UserPosition getLastKnownPosition();

    /**
     * Registers a listener to receive continuous or event-driven position updates.
     */
    void setPositionUpdateListener(PositionUpdateListener listener);

    /**
     * Returns a human-readable identifier for this provider type.
     */
    String getProviderName();
}
