package com.opitz.iotprototype.controller;

import com.opitz.iotprototype.entities.DeviceGroup;
import com.opitz.iotprototype.services.DeviceGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.util.List;

/**
 * User: Pascal
 * Date: 04.04.14
 * Time: 15:23
 */


@Controller
@RequestMapping("/group")
public class DeviceGroupController {

    @Autowired
    DeviceGroupService deviceGroupService;

    @RequestMapping(value = "/{label}", method = RequestMethod.GET)
    public List<DeviceGroup> findByLabel(@PathVariable("label") String label) {
        return deviceGroupService.findByLabel(label);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public Serializable save(DeviceGroup devicegroup) throws Exception {
        return deviceGroupService.save(devicegroup);
    }


    @RequestMapping(method = RequestMethod.POST)
    public void update(DeviceGroup devicegroup) throws Exception {
        deviceGroupService.update(devicegroup);
    }


    @RequestMapping(method = RequestMethod.DELETE)
    public void delete(DeviceGroup deviceGroup) {
        deviceGroupService.delete(deviceGroup);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<DeviceGroup> listAll() {
        return deviceGroupService.listAll();
    }

}
