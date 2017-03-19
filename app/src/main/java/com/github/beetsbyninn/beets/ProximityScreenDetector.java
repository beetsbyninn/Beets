package com.github.beetsbyninn.beets;

/**
 * Interface that gets lux value from the sensor handler classs and send it the BeetsService class
 * The interface is used for turning off/ turning on the screen.
 */
public interface ProximityScreenDetector {
    void onCoverDetected(float luxValueFromSensor);

}
