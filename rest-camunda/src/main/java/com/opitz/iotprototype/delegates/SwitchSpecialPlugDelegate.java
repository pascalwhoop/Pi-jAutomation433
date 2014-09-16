package com.opitz.iotprototype.delegates;

import com.opitz.iotprototype.entities.DeviceGroup;
import com.opitz.iotprototype.entities.ElroPowerPlug;
import com.opitz.iotprototype.services.DeviceGroupService;
import com.opitz.iotprototype.services.ElroPowerPlugService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * Use execution variable 'specials' to determine special {@link DeviceGroup}s
 * and set all contained {@link ElroPowerPlug}s to given state depends on
 * injected field 'switchTo' (on/off).
 * 
 * @author developer
 * 
 */
public class SwitchSpecialPlugDelegate implements JavaDelegate {

	@Autowired
	private DeviceGroupService deviceGroupService;

	@Autowired
	private ElroPowerPlugService elroPowerPlugService;

	// Field injection - see process source
	private Expression switchTo;

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		String switchSpecialsTo = (String) switchTo.getValue(execution);

		System.out.println("## camunda: switch special groups " + switchSpecialsTo
		    + " ##");

		@SuppressWarnings("unchecked")
		Set<String> deviceGroupLabels = (Set<String>) execution
		    .getVariable("specials");

		boolean state = switchSpecialsTo.equals("on") ? true : false;

		// find special plugs
		Set<ElroPowerPlug> allPlugs = new HashSet<>();
		for (String label : deviceGroupLabels) {
			DeviceGroup deviceGroup = deviceGroupService.findByLabel(label);

			if (deviceGroup != null) {
				System.out.println("## camunda: special deviceGroup '"
				    + deviceGroup.getLabel() + "' found ##");
				allPlugs.addAll(deviceGroup.getElroPowerPlugs());
			} else {
				System.out.println("## camunda: special deviceGroup '" + label
				    + "' not found. Processing continues ##");
			}

		}

		// switch special plugs
		System.out.println("## camunda: " + allPlugs.size()
		    + " ElroPowerPlugs in special deviceGroups found to switch to '"
		    + state + "' ##");
		this.switchPlugs(allPlugs, state);
	}

	private void switchPlugs(final Set<ElroPowerPlug> elroPowerPlugs,
	    final boolean state) {
		for (ElroPowerPlug plug : elroPowerPlugs) {
			elroPowerPlugService.setPhysicalStateAndSaveToDB(plug.getId(), state);
		}
	}

}
