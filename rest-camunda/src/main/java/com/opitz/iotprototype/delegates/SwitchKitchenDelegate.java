package com.opitz.iotprototype.delegates;

import java.util.Set;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

import com.opitz.iotprototype.entities.DeviceGroup;
import com.opitz.iotprototype.entities.ElroPowerPlug;
import com.opitz.iotprototype.services.DeviceGroupService;
import com.opitz.iotprototype.services.ElroPowerPlugService;

/**
 * Search for {@link DeviceGroup}s with label 'kitchen' and set all contained
 * {@link ElroPowerPlug}s ON.
 * 
 * @author developer
 * 
 */
public class SwitchKitchenDelegate implements JavaDelegate {

	@Autowired
	private DeviceGroupService deviceGroupService;

	@Autowired
	private ElroPowerPlugService elroPowerPlugService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		System.out.println("## camunda: switch on the kitchen ##");

		// find kitchen plugs
		DeviceGroup deviceGroup = deviceGroupService.findByLabel("kitchen");
		System.out.println("## camunda: deviceGroup 'kitchen' requested - found '"
		    + deviceGroup.getLabel() + "' ##");

		// switch on kitchen plugs
		this.switchOnPlugs(deviceGroup.getElroPowerPlugs());
	}

	private void switchOnPlugs(final Set<ElroPowerPlug> elroPowerPlugs) {
		for (ElroPowerPlug plug : elroPowerPlugs) {
			elroPowerPlugService.setState(plug.getId(), true);
		}
	}

}
