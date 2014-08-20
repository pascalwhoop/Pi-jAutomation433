package com.opitz.iotprototype.utils;

/**
 * created by: OPITZ CONSULTING Deutschland GmbH
 *
 * @author Brokmeier, Pascal
 */
public class DeviceTypeNotFoundException extends Exception {

    public DeviceTypeNotFoundException () {
    }

    public DeviceTypeNotFoundException (String message) {
        super(message);
    }
}
