package com.opitz.iotprototype.controller;

import com.opitz.iotprototype.entities.Device;
import com.opitz.iotprototype.services.DeviceService;
import com.opitz.iotprototype.utils.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This Controller manages all switch interaction, so basically on and off adding deleting and querying.
 * <p/>
 * User: Pascal Date: 07.10.13 Time: 12:31
 */



@Controller
@RequestMapping("/service/device")
public class DeviceController {

    @Autowired
    DeviceService deviceService;

    /**
     * Set state of certain  {@link Device}.
     * <p/>
     * <p/>
     * <pre>
     * <b>REST call example:</b><br/>
     * {@code PUT .../device/<exampleId><br/>
     * </pre>
     *
     * @param device certain {@link Device}
     * @return certain {@link Device} retrieved from the DB after updating the object
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Device save(@RequestBody Device device) {
        deviceService.save(device);
        return deviceService.find(device.getId());
    }

    /**
     * Set state of certain  {@link Device}.
     * <p/>
     * <p/>
     * <pre>
     * <b>REST call example:</b><br/>
     * {@code PUT .../device/<exampleId><br/>
     * </pre>
     *
     * @param device certain {@link Device}
     * @return certain {@link Device} retrieved from the DB after updating the object
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Device create(@RequestBody Device device) {
        deviceService.save(device);
        return deviceService.find(device.getId());
    }

    /**
     * Get certain  {@link Device}.
     * <p/>
     * <p/>
     * <pre>
     * <b>REST call example:</b><br/>
     * {@code GET .../device/<exampleId><br/>
     * </pre>
     *
     * @param device certain {@link Device}
     * @return certain {@link Device} retrieved from the DB after updating the object
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Device find(@RequestParam("id") Integer id) {
        return deviceService.find(id);
    }


    /**
     * Retrieve all {@link Device}s. (TODO available to the requesting user)
     * <p/>
     * <p/>
     * <pre>
     * <b>REST call example:</b><br/>
     * {@code GET .../device}
     * </pre>
     *
     * @return {@link List} of {@link Device}
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public List<Device> getAll() {
        return deviceService.findAll();
    }


    /**
     * Delete {@link Device} by id from system.
     * <p/>
     * <p/>
     * <pre>
     * <b>REST call example:</b><br/>
     * {@code DELETE .../switches/<exampleId>}
     * </pre>
     *
     * @param id unique {@link Device} identifier
     * @throws DataNotFoundException
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id) throws DataNotFoundException {
        deviceService.remove(deviceService.find(id));
    }

}
