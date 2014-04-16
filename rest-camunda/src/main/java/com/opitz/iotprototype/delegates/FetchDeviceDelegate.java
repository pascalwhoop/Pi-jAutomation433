package com.opitz.iotprototype.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import com.opitz.iotprototype.entities.ElroPowerPlug;

public class FetchDeviceDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		System.out.println("## camunda: fetching plugs for "
				+ execution.getVariables().get("username") + "...requested state is "
				+ execution.getVariables().get("state") + " ##");

		// TODO determine devices by username and pass
		// TODO maybe split to several executions per device or pass a collection
		ElroPowerPlug myPlug = new ElroPowerPlug();
		myPlug.setGroupID("group-1");
		myPlug.setId(110);
		myPlug.setLastKnownState(true);
		execution.setVariable("plug", myPlug);
		System.out
				.println("## camunda: created a plug with id 110 and lastKnownState true on-the-fly for demonstration purpose ##");
	}

}
