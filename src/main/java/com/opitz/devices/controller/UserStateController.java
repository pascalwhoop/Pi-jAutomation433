package com.opitz.devices.controller;

import com.opitz.devices.services.NetworkNodeService;
import org.krakenapps.pcap.decoder.ethernet.MacAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;
import java.util.Set;

/**
 * User: Pascal
 * Date: 03.09.13
 * Time: 22:06
 */

@Controller
@RequestMapping("/service/userstate")
public class UserStateController {

    @Autowired
    NetworkNodeService networkNodeService;


    //@Autowired
    //private RuntimeService runtimeService;

    /**
     *
     * Method to set the current state of the user. For now only basic functionality
     * @param username
     * @param state expected states: present, absent
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/{username}/setuserstate", method = RequestMethod.POST)
    public String setUserState(@PathVariable("username") String username, @RequestParam String state){
        return "foo";
    }

    @ResponseBody
    @RequestMapping(value="/findAllDevices", method=RequestMethod.GET)
    public Map<MacAddress, InetAddress> findAllDevicesOnLocalNetwork() throws IOException{
        return networkNodeService.getAllDevicesWithARPing();
    }

    @ResponseBody
    @RequestMapping(value = "/findallbyping", method = RequestMethod.GET)
    public Set<String> findAllByPing(){
        return networkNodeService.pingAndQueryArp();
    }


    /*@ResponseBody
    @RequestMapping(value="/triggerprocess", method = RequestMethod.GET)
    public String triggerProcess(){
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("userstatechange");
        System.out.print(processInstance.toString());
        return processInstance.toString();

    }*/




}
