package com.opitz.devices.utils;

import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Pascal
 * Date: 02.05.13
 * Time: 13:46
 * To change this template use File | Settings | File Templates.
 */
@Component
public class BasicAuthenticationDecrypter {

    public BASE64Decoder getDecoder() {
        return decoder;
    }

    public void setDecoder(BASE64Decoder decoder) {
        this.decoder = decoder;
    }

    //TODO
    //Spring nimmt glaueb ich die header raus

    sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();

    public String getUsernameFromBasicHeader(String header) throws IOException {

        String decryptedBasic = new String(decoder.decodeBuffer(header));
        int seperatorIndex = decryptedBasic.indexOf(":");
        if (seperatorIndex > 0) {
            return decryptedBasic.substring(0, seperatorIndex - 1);
        } else {
            throw new IOException("Something is wrong with your Basic header");
        }

    }

    public String getPasswordFromBasicHeader(String header) throws IOException {
        String decryptedBasic = new String(decoder.decodeBuffer(header));
        int seperatorIndex = decryptedBasic.indexOf(":");
        if (seperatorIndex > 0) {
            return decryptedBasic.substring(seperatorIndex + 1);
        } else {
            throw new IOException("Something is wrong with your Basic header");
        }
    }
}
