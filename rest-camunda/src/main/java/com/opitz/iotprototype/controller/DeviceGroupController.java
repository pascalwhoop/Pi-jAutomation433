package com.opitz.iotprototype.controller;

import com.opitz.iotprototype.entities.DeviceGroup;
import com.opitz.iotprototype.messages.DeviceGroupJSONMessage;
import com.opitz.iotprototype.services.DeviceGroupService;
import com.opitz.iotprototype.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * {@link DeviceGroup} controller.
 * 
 * User: Pascal Date: 04.04.14 Time: 15:23
 */

@Controller
@RequestMapping("/service/groups")
public class DeviceGroupController {

	@Autowired
	DeviceGroupService deviceGroupService;

	@Autowired
	UserService userService;

	/**
	 * Retrieve {@link DeviceGroup} matched by id.
	 * <p>
	 * 
	 * <pre>
	 * <b>REST call example:</b><br/>
	 * {@code GET .../groups/label/<exampleLabel>}
	 * </pre>
	 * 
	 * @return {@link List} of {@link DeviceGroup}
	 */
	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public DeviceGroupJSONMessage findByID(@PathVariable("id") Integer id) {
		return deviceGroupService.getDeviceGroupJSONMessageByID(id);
	}

	/**
	 * Create a new {@link DeviceGroup}.
	 * <p>
	 * 
	 * <pre>
	 * <b>REST call example:</b><br/>
	 * {@code POST .../groups}<br/>
	 * and {@link DeviceGroup} as {@link RequestBody}
	 * </pre>
	 * 
	 * @param deviceGroupJSONMessage
	 *          new {@link DeviceGroupJSONMessage}
	 * 
	 * @return unique identifier of created {@link DeviceGroup}
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public Integer create(@RequestBody DeviceGroupJSONMessage deviceGroupJSONMessage) throws Exception {
		return (Integer) deviceGroupService.saveWithJSONMessage(deviceGroupJSONMessage);
	}

	/**
	 * Update a certain {@link DeviceGroup}.
	 * <p>
	 * 
	 * <pre>
	 * <b>REST call example:</b><br/>
	 * {@code PUT .../groups<br/>
	 * and {@link DeviceGroup} as {@link RequestBody}
	 * </pre>
	 * 
	 * @param devicegroup
	 *          {@link DeviceGroup} with new values
	 * 
	 * @return unique identifier of created {@link DeviceGroup}
	 */
	@ResponseBody
	@RequestMapping(value="/{id}" , method = RequestMethod.POST)
	public Integer update(@RequestBody DeviceGroup devicegroup) throws Exception {
		deviceGroupService.update(devicegroup);
		return devicegroup.getId();
	}

	/**
	 * Delete a certain {@link DeviceGroup} by id.
	 * <p>
	 * 
	 * <pre>
	 * <b>REST call example:</b><br/>
	 * {@code DELETE .../groups/<exampleId>}
	 * </pre>
	 * 
	 * @param id
	 *          unique identifier
	 */
	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable("id") Integer id) {
		deviceGroupService.delete(id);
	}

	/**
	 * Retrieve all {@link DeviceGroup}s.
	 * <p>
	 * 
	 * <pre>
	 * <b>REST call example:</b><br/>
	 * {@code GET .../groups}
	 * </pre>
	 * 
	 * @return {@link List} of {@link DeviceGroup}
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public List<DeviceGroupJSONMessage> listAllInJSON() {
		return deviceGroupService.listAllJSON();
	}


}
