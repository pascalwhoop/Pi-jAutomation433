package com.opitz.devices.controller;

import com.opitz.devices.entities.ElroPowerPlug;
import com.opitz.jni.NativeRCSwitchAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public void testStaticAddress(@PathVariable("switchID") String switchID){

        NativeRCSwitchAdapter jniAdapter= NativeRCSwitchAdapter.getInstance();
        jniAdapter.switchOn("11111", "10101");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {}
        jniAdapter.switchOff("11111", "10101");

    }

    @ResponseBody
    @RequestMapping(value="/activate/{groupID}/{switchID}", method = RequestMethod.POST)
    public void activateSwitch(@PathVariable("switchID") String switchID,@PathVariable("groupID") String groupID){

        NativeRCSwitchAdapter jniAdapter= NativeRCSwitchAdapter.getInstance();
        jniAdapter.switchOn(groupID, switchID);
    }

    @ResponseBody
    @RequestMapping(value="/deactivate/{groupID}/{switchID}", method = RequestMethod.POST)
    public void deactivateSwitch(@PathVariable("switchID") String switchID,@PathVariable("groupID") String groupID){

        NativeRCSwitchAdapter jniAdapter = NativeRCSwitchAdapter.getInstance();
        jniAdapter.switchOff(groupID, switchID);
    }

    @ResponseBody
    @RequestMapping(value="/addplug", method = RequestMethod.POST)
    public void addSwitch(ElroPowerPlug newPlug){


    }
}


//01111 01111
