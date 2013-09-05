package com.opitz.bpmEngine.devices.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * User: Pascal
 * Date: 03.09.13
 * Time: 22:06
 */

@Controller
@RequestMapping("/service/userstate")
public class UserStateController {


    /**
     *
     * Method to set the current state of the user. For now only basic functionality
     * @param username
     * @param state expected states: present, absent
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/{username}/setuserstate", method = RequestMethod.POST)
    public boolean setUserState(@PathVariable("username") String username, @RequestParam String state){

        return true;
    }




}
