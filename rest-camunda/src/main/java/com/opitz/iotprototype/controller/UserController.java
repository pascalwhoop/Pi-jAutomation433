package com.opitz.iotprototype.controller;

import java.util.HashMap;
import java.util.List;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.opitz.iotprototype.entities.User;
import com.opitz.iotprototype.entities.UserState;
import com.opitz.iotprototype.services.NetworkNodeService;
import com.opitz.iotprototype.services.UserService;

/**
 * {@link User} controller.
 * 
 * User: Pascal Date: 03.09.13 Time: 22:06
 */

@Controller
@RequestMapping("/service/users")
public class UserController {

	private static final String PLUG_SWITCH_PROCESS_DEFINITION_KEY = "plug-switch-process";

	@Autowired
	NetworkNodeService networkNodeService;

	@Autowired
	UserService userService;

	@Autowired
	RuntimeService runtimeService;

	/**
	 * Set current state of a certain {@link User} by starting a new process
	 * instance.
	 * <p>
	 * 
	 * <pre>
	 * <b>REST call example:</b><br/>
	 * {@code PUT .../users/<exampleUsername>/state/<exampleState>}<br/>
	 * and {@link User} as {@link RequestBody}
	 * </pre>
	 * 
	 * @param username
	 *          unique user name
	 * @param state
	 *          expected states: online or offline
	 * @return process instance id
	 */
	@ResponseBody
	@RequestMapping(value = "/{username}/state/{setState}", method = RequestMethod.PUT)
	public String setUserState(@PathVariable("username") String username,
	    @PathVariable("setState") String state) {
		return this.startPlugSwitchProcess(username,
		    UserState.valueOf(state.toUpperCase()));
	}

	/**
	 * Start plug switch process and return process instance id. (e.g. id can be
	 * used to get more info about the instance by the history service)
	 * 
	 * @param username
	 *          unique user name
	 * @param state
	 *          requested {@link UserState}, allowed are {@link UserState#ONLINE}
	 *          or {@link UserState#OFFLINE}
	 * @return unique process instance id
	 */
	private String startPlugSwitchProcess(String username, UserState state) {
		HashMap<String, Object> variables = new HashMap<>();
		variables.put("username", username);
		variables.put("state", state.name().toLowerCase()); // TODO change to enum
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(
		    PLUG_SWITCH_PROCESS_DEFINITION_KEY, variables);
		return "process instance with id " + processInstance.getId() + " done.";
	}

	/**
	 * Create a new {@link User}.
	 * <p>
	 * Please note that when adding a new user via rest, there must be a network
	 * node supplied with the user. so first fetch all network nodes from the api,
	 * then select the one that should be the one that the user usually carries on
	 * him (a smartphone e.g.) and then add the user with this method.
	 * <p>
	 * 
	 * <pre>
	 * <b>REST call example:</b><br/>
	 * {@code POST .../users}<br/>
	 * and {@link User} as {@link RequestBody}
	 * </pre>
	 * 
	 * @param user
	 *          new {@link User}
	 * 
	 * @return created {@link User}
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public User create(@RequestBody User user) {
		userService.save(user);
		return userService.load(user.getUsername());
	}

	/**
	 * Update a {@link User}.
	 * <p>
	 * 
	 * <pre>
	 * <b>REST call example:</b><br/>
	 * {@code PUT .../users<br/>
	 * and {@link User} as {@link RequestBody}
	 * </pre>
	 * 
	 * @param user
	 *          {@link User} with new values
	 * 
	 * @return updated {@link User}
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.PUT)
	public User update(@RequestBody User user) {
		userService.update(user);
		return userService.load(user.getUsername());
	}

	/**
	 * Delete {@link User} by user name.
	 * <p>
	 * 
	 * <pre>
	 * <b>REST call example:</b><br/>
	 * {@code DELETE .../users/<exampleUserName>}
	 * </pre>
	 * 
	 * @param username
	 *          user name
	 * 
	 * @return true if deletion successful, otherwise false
	 */
	@ResponseBody
	@RequestMapping(value = "/{username}", method = RequestMethod.DELETE)
	public boolean delete(@PathVariable("username") String username) {
		User user = userService.load(username);
		try {
			userService.delete(user);
			return true;
		} catch (Exception e) {
			return false; // TODO change return type to {@link
			// HttpServletResponse}
		}
	}

	/**
	 * Retrieve all {@link User}.
	 * <p>
	 * 
	 * <pre>
	 * <b>REST call example:</b><br/>
	 * {@code GET .../users}
	 * </pre>
	 * 
	 * @return {@link List} of {@link User}
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public List<User> getAll() {
		return userService.listAll();
	}

	/**
	 * Retrieve all user MAC address (devices).
	 * <p>
	 * 
	 * <pre>
	 * <b>REST call example:</b><br/>
	 * {@code GET .../users/devices}
	 * </pre>
	 * 
	 * @return {@link HashMap} of form Entry(MacAddress, Username)
	 */
	@ResponseBody
	@RequestMapping(value = "/devices", method = RequestMethod.GET)
	public HashMap<String, String> getAllDevices() {
		return userService.getDeviceMACUserMap();
	}

	/**
	 * Retrieve {@link User} by user name.
	 * <p>
	 * 
	 * <pre>
	 * <b>REST call example:</b><br/>
	 * {@code GET .../users/username/<exampleUserName>}
	 * </pre>
	 * 
	 * 
	 * @param username
	 *          user name
	 * 
	 * @return {@link User}
	 */
	@ResponseBody
	@RequestMapping(value = "/username/{username}", method = RequestMethod.GET)
	public User findByUsername(@PathVariable("username") String username) {
		return userService.load(username);
	}

}
