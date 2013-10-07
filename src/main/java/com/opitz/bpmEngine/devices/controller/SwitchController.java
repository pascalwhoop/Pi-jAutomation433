package com.opitz.bpmEngine.devices.controller;

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


    }
}
