package com.opitz.iotprototype.delegates;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

import com.opitz.iotprototype.entities.DeviceGroup;
import com.opitz.iotprototype.entities.ElroPowerPlug;
import com.opitz.iotprototype.services.ElroPowerPlugService;

/**
 * Switch off all {@link ElroPowerPlug}s independently any {@link DeviceGroup}.
 * 
 * @author developer
 * 
 */
public class SwitchOffAllPlugDelegate implements JavaDelegate {

	@Autowired
	private ElroPowerPlugService elroPowerPlugService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		System.out.println("## camunda: switch OFF all plugs ##");

		// find all plugs
		List<ElroPowerPlug> elroPowerPlugs = elroPowerPlugService.listAll();
		System.out.println("## camunda: " + elroPowerPlugs.size()
		    + " plugs total exist to switch OFF ##");

		// switch off all plugs
		this.switchOffPlugs(elroPowerPlugs);
	}

	private void switchOffPlugs(final List<ElroPowerPlug> elroPowerPlugs) {
		for (ElroPowerPlug plug : elroPowerPlugs) {
			elroPowerPlugService.setPhysicalStateAndSaveToDB(plug.getId(), false);
		}
	}

}
