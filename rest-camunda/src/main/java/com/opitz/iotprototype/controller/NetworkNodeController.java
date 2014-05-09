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
 * {@link NetworkNode} controller.
 * 
 * User: Pascal Date: 27.03.14 Time: 10:32
 */

@Controller
@RequestMapping("/service/networkdevices")
public class NetworkNodeController {

	@Autowired
	NetworkNodeService networkNodeService;

	/**
	 * Retrieve all {@link NetworkNode}.
	 * <p>
	 * 
	 * <pre>
	 * <b>REST call example:</b><br/>
	 * {@code GET .../networkdevices}
	 * </pre>
	 * 
	 * @return {@link HashMap} of MAC address as key and {@link NetworkNode} as
	 *         value.
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public HashMap<String, NetworkNode> getAll() {
		return networkNodeService.getAllStoredDevices();
	}

	/**
	 * Determine own ip address.
	 * <p>
	 * 
	 * <pre>
	 * <b>REST call example:</b><br/>
	 * {@code GET .../networkdevices/ownip}
	 * </pre>
	 * 
	 * @param request
	 *            {@link HttpServletRequest}
	 * @return ip address
	 */
	@ResponseBody
	@RequestMapping(value = "/ownip", method = RequestMethod.GET)
	public String getOwnIp(HttpServletRequest request) {
		return request.getRemoteAddr();
	}

	/**
	 * Create several new {@link NetworkNode}s.
	 * <p>
	 * 
	 * <pre>
	 * <b>REST call example:</b><br/>
	 * {@code POST .../networkdevices/addmultiple}
	 * and list of {@link NetworkNode} as {@link RequestBody}
	 * </pre>
	 * 
	 * @param request
	 *            {@link HttpServletRequest}
	 */
	@ResponseBody
	@RequestMapping(value = "/addmultiple", method = RequestMethod.POST)
	public void addMultiple(@RequestBody List<NetworkNode> networkNodes) {
		networkNodeService.storeAnyNewDevices(networkNodes);
	}
}
