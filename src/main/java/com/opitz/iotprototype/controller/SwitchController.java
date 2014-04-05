package com.opitz.iotprototype.controller;

import com.opitz.iotprototype.entities.ElroPowerPlug;
import com.opitz.iotprototype.services.ElroPowerPlugService;
import com.opitz.iotprototype.utils.DataNotFoundException;
import org.camunda.bpm.engine.ProcessEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * User: Pascal
 * Date: 07.10.13
 * Time: 12:31
 * <p/>
 * This Controller manages all switch interaction, so basically on and off adding deleting and querying.
 * TODO scheduling plug actions
 */

@Controller
@RequestMapping("/service/switch")
public class SwitchController {


    @Autowired
    ElroPowerPlugService elroPowerPlugService;

    @Autowired
    ProcessEngine processEngine;

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

    /**
     * REST call with PUT to activate a certain plug.
     *
     * @param plug a JSON representation of a ElroPowerPlug
     * @return The same plug retreived from the DB after updating the lastKnownState
     */

    @ResponseBody
    @RequestMapping(value = "/activate", method = RequestMethod.PUT)
    public ElroPowerPlug activateSwitch(@RequestBody ElroPowerPlug plug) {

        HashMap<String, Object> processInformation = new HashMap();
        processInformation.put("plugId", plug.getId());
        processInformation.put("newState", true);
        processEngine.getRuntimeService().startProcessInstanceByKey("setPlugStateProcess", processInformation);

        return elroPowerPlugService.load(plug.getId());
        //return elroPowerPlugService.setState(plug, true);
    }


    /**
     * REST call with PUT to deactivate a certain plug.
     *
     * @param plug a JSON representation of a ElroPowerPlug
     * @return The same plug retreived from the DB after updating the lastKnownState
     */

    @ResponseBody
    @RequestMapping(value = "/deactivate", method = RequestMethod.PUT)
    public ElroPowerPlug deactivateSwitch(@RequestBody ElroPowerPlug plug) {

        HashMap<String, Object> processInformation = new HashMap();
        processInformation.put("plugId", plug.getId());
        processInformation.put("newState", false);
        processEngine.getRuntimeService().startProcessInstanceByKey("setPlugStateProcess", processInformation);

        return elroPowerPlugService.load(plug.getId());
        //return elroPowerPlugService.setState(plug, false);
    }

    /**
     * REST call with GET to retreive all plugs (TODO available to the requesting user )
     *
     * @return A List of all plugs
     */

    @ResponseBody
    @RequestMapping(value = "/getAllPlugsAvailable", method = RequestMethod.GET)
    public List<ElroPowerPlug> getAllPlugsAvailable() {
        return elroPowerPlugService.listAll();
    }


    @ResponseBody
    @RequestMapping(value = "/timer/{time}/activate")
    public boolean setTimerForPlug(@RequestBody ElroPowerPlug plug, @PathVariable("time") String timeString) {
        Date time = new Date(Long.decode(timeString));
        return false;
    }


    /**
     * REST call with POST to post a new plug to the system.
     *
     * @param newPlug
     */


    @ResponseBody
    @RequestMapping(value = "/addplug", method = RequestMethod.POST)
    public ElroPowerPlug addSwitch(@RequestBody ElroPowerPlug newPlug) {
        Integer id = (Integer) elroPowerPlugService.save(newPlug);
        return elroPowerPlugService.load(id);
    }

    /**
     * REST call with DELETE to delete a plug from the system.
     *
     * @param id
     * @throws DataNotFoundException
     */

    @ResponseBody
    @RequestMapping(value = "/deleteplug/{id}", method = RequestMethod.DELETE)
    public void deleteSwitch(@PathVariable("id") Integer id) throws DataNotFoundException {
        elroPowerPlugService.delete(id);
    }


}


