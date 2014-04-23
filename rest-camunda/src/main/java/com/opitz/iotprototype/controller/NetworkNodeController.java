package com.opitz.iotprototype.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.opitz.iotprototype.entities.NetworkNode;
import com.opitz.iotprototype.services.NetworkNodeService;

/**
 * User: Pascal Date: 27.03.14 Time: 10:32
 */

@Controller
@RequestMapping("/service/networkdevices")
public class NetworkNodeController {

	@Autowired
	NetworkNodeService networkNodeService;

	/**
	 * Retrieve all known network nodes.
	 * 
	 * @return a list of all known nodes
	 */
	@ResponseBody
	@RequestMapping(value = "/getall", method = RequestMethod.GET)
	public HashMap<String, NetworkNode> findAllByPing() {
		return networkNodeService.getAllStoredDevices();
	}

	@ResponseBody
	@RequestMapping(value = "/getownip", method = RequestMethod.GET)
	public String getOwnIp(HttpServletRequest request) {
		return request.getRemoteAddr();
	}

	@ResponseBody
	@RequestMapping(value = "/addmultiple", method = RequestMethod.POST)
	public void addMultiple(@RequestBody List<NetworkNode> networkNodes) {
		networkNodeService.storeAnyNewDevices(networkNodes);
	}
}
