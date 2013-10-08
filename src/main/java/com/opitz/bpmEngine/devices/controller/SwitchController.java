package com.opitz.bpmEngine.devices.controller;

import com.opitz.jni.NativeRCSwitchAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * User: Pascal
 * Date: 07.10.13
 * Time: 12:31
 */

@Controller
@RequestMapping("/service/switch")
public class SwitchController {

    @ResponseBody
    @RequestMapping(value="/{switchID}/activate", method = RequestMethod.POST)
    public void activateSwitch(@PathVariable("switchID") String switchID){

        NativeRCSwitchAdapter jniAdapter= NativeRCSwitchAdapter.getInstance();
        jniAdapter.switchOn("11111", "10101");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {}
        jniAdapter.switchOff("11111", "10101");

    }
}
