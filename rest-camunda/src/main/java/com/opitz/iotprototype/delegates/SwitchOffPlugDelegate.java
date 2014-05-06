package com.opitz.iotprototype.delegates;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

import com.opitz.iotprototype.entities.DeviceGroup;
import com.opitz.iotprototype.entities.ElroPowerPlug;
import com.opitz.iotprototype.entities.User;
import com.opitz.iotprototype.entities.UserState;
import com.opitz.iotprototype.services.DeviceGroupService;
import com.opitz.iotprototype.services.ElroPowerPlugService;

/**
 * 
 * Determine all {@link DeviceGroup}s and search on it for {@link User} matches
 * with execution variable 'username'.
 * 
 * Switch off any {@link ElroPowerPlug} if nobody else need it from current
 * {@link DeviceGroup}, otherwise switch for this {@link DeviceGroup} is
 * skipped.<br/>
 * <br/>
 * 
 * @author developer
 * 
 */
public class SwitchOffPlugDelegate implements JavaDelegate {

	@Autowired
	private DeviceGroupService deviceGroupService;

	@Autowired
	private ElroPowerPlugService elroPowerPlugService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		System.out.println("## camunda: switch off the grouped plugs ##");

		String username = (String) execution.getVariable("username");

		List<DeviceGroup> deviceGroups = deviceGroupService.listAll();

		// find users plugs
		Set<ElroPowerPlug> allPlugs = new HashSet<>();
		for (DeviceGroup group : deviceGroups) {
			for (User user : group.getUsersWithAccess()) {
				if (username.equals(user.getUsername())) {
					allPlugs.addAll(this.determineDefeatablePlugs(group, user));
				}
				continue;
			}
		}

		// switch off users plugs
		System.out.println("## camunda: " + allPlugs.size()
		    + " ElroPowerPlugs found to switch OFF ##");
		this.switchOffPlugs(allPlugs);
	}

	private Set<ElroPowerPlug> determineDefeatablePlugs(DeviceGroup currentGroup,
	    User currentUser) {
		for (User user : currentGroup.getUsersWithAccess()) {
			// check if someone is online in group
			if ((!currentUser.getUsername().equals(user.getUsername()))
			    && user.getState().equals(UserState.ONLINE)) {
				return Collections.<ElroPowerPlug> emptySet();
			}
		}
		return currentGroup.getElroPowerPlugs();
		// TODO: Currently the case where a {@link ElroPowerPlug} is referenced by
		// several {@link DeviceGroup}s is excluded from implemetation and should
		// be implemented in a future release.
	}

	private void switchOffPlugs(final Set<ElroPowerPlug> elroPowerPlugs) {
		for (ElroPowerPlug plug : elroPowerPlugs) {
			elroPowerPlugService.setState(plug.getId(), false);
		}
	}

}
