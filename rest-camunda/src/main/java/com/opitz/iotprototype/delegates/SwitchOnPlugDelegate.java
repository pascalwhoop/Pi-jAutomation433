package com.opitz.iotprototype.delegates;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

import com.opitz.iotprototype.entities.DeviceGroup;
import com.opitz.iotprototype.entities.ElroPowerPlug;
import com.opitz.iotprototype.entities.User;
import com.opitz.iotprototype.services.DeviceGroupService;
import com.opitz.iotprototype.services.ElroPowerPlugService;

/**
 * Determine all {@link DeviceGroup}s and search on it for {@link User} matches
 * with execution variable 'username'. Switch ON succesful match results.<br/>
 * <br/>
 * TODO: Currently {@link User} do not reference his {@link DeviceGroup}s.
 * Update implementation if this is possible in future releases.
 * 
 * @author developer
 * 
 */
public class SwitchOnPlugDelegate implements JavaDelegate {

	@Autowired
	private DeviceGroupService deviceGroupService;

	@Autowired
	private ElroPowerPlugService elroPowerPlugService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		System.out.println("## camunda: switch on the grouped plugs ##");

		String username = (String) execution.getVariable("username");

		List<DeviceGroup> deviceGroups = deviceGroupService.listAll();

		// find users plugs
		Set<ElroPowerPlug> allPlugs = new HashSet<>();
		for (DeviceGroup group : deviceGroups) {
			for (User user : group.getUsersWithAccess()) {
				if (username.equals(user.getUsername())) {
					allPlugs.addAll(group.getElroPowerPlugs());
				}
			}
		}

		// switch on users plugs
		System.out.println("## camunda: " + allPlugs.size()
		    + " ElroPowerPlugs found to switch ON ##");
		this.switchOnPlugs(allPlugs);
	}

	private void switchOnPlugs(final Set<ElroPowerPlug> elroPowerPlugs) {
		for (ElroPowerPlug plug : elroPowerPlugs) {
			// elroPowerPlugService.setState(plug.getId(), true);
			System.out.println("## camunda: TEST set plug state to true");
		}
	}

}
