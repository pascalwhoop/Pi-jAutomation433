package com.opitz.iotprototype.utils;

/**
 * created by: OPITZ CONSULTING Deutschland GmbH
 *
 * @author Brokmeier, Pascal
 */
public class DeviceNotFoundException extends Exception {

    public DeviceNotFoundException () {
    }

    public DeviceNotFoundException (String message) {
        super(message);
    }
}