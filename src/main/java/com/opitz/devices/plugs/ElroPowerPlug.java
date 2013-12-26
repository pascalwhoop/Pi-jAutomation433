package com.opitz.devices.plugs;

/**
 * A ELRO Power Plug has 2 times 5 DIP switches which can either be set to 0 or 1.
 * The first 5 DIP's are a "system" code and the second 5 are a "unit" code.
 * However it doesn't matter how you encode it, all that matters is that you don't have
 * more than 1 plug with the same code (unless you explicitly intend to do so)
 *
 * One could use the System codes to represent seperate rooms but this can also be abstracted in the middleware.
 */
public class ElroPowerPlug {



}
