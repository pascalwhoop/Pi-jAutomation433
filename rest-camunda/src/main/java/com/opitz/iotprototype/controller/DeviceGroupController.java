package com.opitz.iotprototype.controller;

import com.opitz.iotprototype.entities.DeviceGroup;
import com.opitz.iotprototype.entities.ElroPowerPlug;
import com.opitz.iotprototype.entities.User;
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
	 * Retrieve {@link DeviceGroup} matched by label.
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
	@RequestMapping(value = "/label/{label}", method = RequestMethod.GET)
	public DeviceGroupJSONMessage findByLabel(@PathVariable("label") String label) {
		return deviceGroupService.getDeviceGroupForJSONByLabel(label);
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
	@RequestMapping(method = RequestMethod.PUT)
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

	/**
	 * Update a certain {@link DeviceGroup} by an existing {@link User}.
	 * <p>
	 * 
	 * <pre>
	 * <b>REST call example:</b><br/>
	 * {@code PUT .../groups/<exampleGroupId>/users/<exampleUsername><br/>
	 * </pre>
	 * 
	 * @param groupId
	 *          unique existing {@link DeviceGroup} id
	 * @param username
	 *          unique existing user name
	 * 
	 * @return unique identifier of updated {@link DeviceGroup}
	 */
	@ResponseBody
	@RequestMapping(value = "/{id}/users/{username}", method = RequestMethod.PUT)
	public Integer addUser(@PathVariable("id") Integer groupId,
	    @PathVariable("username") String username) {
		deviceGroupService.addUser(groupId, username);
		return groupId;
	}

	/**
	 * Update a certain {@link DeviceGroup} by an existing {@link ElroPowerPlug}.
	 * <p>
	 * 
	 * <pre>
	 * <b>REST call example:</b><br/>
	 * {@code PUT .../groups/<exampleGroupId>/switches/<exampleSwitchId><br/>
	 * </pre>
	 * 
	 * @param groupId
	 *          unique existing {@link DeviceGroup} id
	 * @param switchId
	 *          unique existing {@link ElroPowerPlug} id
	 * 
	 * @return unique identifier of updated {@link DeviceGroup}
	 */
	@ResponseBody
	@RequestMapping(value = "/{id}/switches/{switchId}", method = RequestMethod.PUT)
	public Integer addSwitch(@PathVariable("id") Integer groupId,
	    @PathVariable("switchId") Integer switchId) {
		deviceGroupService.addPlug(groupId, switchId);
		return groupId;
	}

}
