package com.opitz.iotprototype.controller;

import com.opitz.iotprototype.entities.User;
import com.opitz.iotprototype.services.NetworkNodeService;
import com.opitz.iotprototype.services.UserService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * User: Pascal Date: 03.09.13 Time: 22:06
 */

@Controller
@RequestMapping("/service/user")
public class UserController {

	private static final String PLUG_SWITCH_PROCESS_DEFINITION_KEY = "plug-switch-process";
	@Autowired
	NetworkNodeService networkNodeService;
	@Autowired
	UserService userService;

	@Autowired
	RuntimeService runtimeService;

	/**
	 * 
	 * Method to set the current state of the user. For now only basic
	 * functionality
	 * 
	 * @param username
	 * @param state
	 *          expected states: present, absent
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/{username}/state/{setState}", method = RequestMethod.POST)
	public String setUserState(@PathVariable("username") String username,
			@PathVariable("setState") String state) {
		return this.startPlugSwitchProcess(username, state);
	}

	/**
	 * Start plug switch process and return process instance id. (e.g. id can be
	 * used to get more info about the instance by the history service)
	 * 
	 * @param username
	 *          user name
	 * @param state
	 *          requested state
	 * @return unique process instance id
	 */
	private String startPlugSwitchProcess(String username, String state) {
		HashMap<String, Object> variables = new HashMap<>();
		variables.put("username", username);
		variables.put("state", state);
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(
				PLUG_SWITCH_PROCESS_DEFINITION_KEY, variables);
		return "process instance with id " + processInstance.getId() + " started.";
	}

	/**
	 * Please note that when adding a new user via rest, there must be a network
	 * node supplied with the user. so first fetch all network nodes from the api,
	 * then select the one that should be the one that the user usually carries on
	 * him (a smartphone e.g.) and then add the user with this method.
	 * 
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.PUT)
	public User addNewUser(@RequestBody User user) {
		userService.save(user);
		return userService.load(user.getUsername());
	}

	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public User updateUser(@RequestBody User user) {
		userService.update(user);
		return userService.load(user.getUsername());
	}

	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public boolean deleteUser(@RequestBody User user) {
		try {
			userService.delete(user);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

    @Deprecated
	@ResponseBody
	@RequestMapping(value = "/getall", method = RequestMethod.GET)
	public List<User> getAll() {
		return this.getAllUsers();
	}

    @ResponseBody
    @RequestMapping(value="/", method = RequestMethod.GET)
    public List<User> getAllUsers(){
        return userService.listAll();
    }

	@ResponseBody
	@RequestMapping(value = "/getbyusername/{username}", method = RequestMethod.GET)
	public User getByUsername(@PathVariable String username) {
		return userService.load(username);
	}

	@ResponseBody
	@RequestMapping(value = "/{username}/state", method = RequestMethod.GET)
	public String getUserState(@PathVariable String username) {
		return userService.retrieveUserStatusByUsername(username);
	}

	@ResponseBody
	@RequestMapping(value = "/all/state", method = RequestMethod.GET)
	public HashMap<String, String> getAllUserStates() {
		return userService.retrieveAllUserStatuses();
	}

}
