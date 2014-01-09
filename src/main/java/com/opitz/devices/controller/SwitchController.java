package com.opitz.devices.controller;

import com.opitz.devices.entities.ElroPowerPlug;
import com.opitz.devices.services.ElroPowerPlugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * User: Pascal
 * Date: 07.10.13
 * Time: 12:31
 */

@Controller
@RequestMapping("/service/switch")
public class SwitchController {


    @Autowired
    ElroPowerPlugService elroPowerPlugService;

    /*@ResponseBody
    @RequestMapping(value="/{switchID}/activate", method = RequestMethod.POST)
    public void testStaticAddress(@PathVariable("switchID") String switchID){

        NativeRCSwitchAdapter jniAdapter= NativeRCSwitchAdapter.getInstance();
        jniAdapter.switchOn("11111", "10101");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {}
        jniAdapter.switchOff("11111", "10101");

    }*/

    @ResponseBody
    @RequestMapping(value="/activate", method = RequestMethod.PUT)
    public ElroPowerPlug activateSwitch(@RequestBody ElroPowerPlug plug){
        return elroPowerPlugService.setState(plug, true);
    }

    @ResponseBody
    @RequestMapping(value="/deactivate", method = RequestMethod.PUT)
    public ElroPowerPlug deactivateSwitch(@RequestBody ElroPowerPlug plug){
        return elroPowerPlugService.setState(plug, false);
    }

    @ResponseBody
    @RequestMapping(value="/getAllPlugsAvailable", method = RequestMethod.GET)
    public List<ElroPowerPlug> getAllPlugsAvailable(){
        return elroPowerPlugService.listAll();
    }

    @ResponseBody
    @RequestMapping(value="/addplug", method = RequestMethod.POST)
    public void addSwitch(@RequestBody ElroPowerPlug newPlug){
        elroPowerPlugService.save(newPlug);
    }





}


//01111 01111
