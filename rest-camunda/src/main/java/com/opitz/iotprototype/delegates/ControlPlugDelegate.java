package com.opitz.iotprototype.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

import com.opitz.iotprototype.entities.ElroPowerPlug;
import com.opitz.iotprototype.services.ElroPowerPlugService;

public class ControlPlugDelegate implements JavaDelegate {

	@Autowired
	ElroPowerPlugService elroPowerPlugService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		ElroPowerPlug elroPowerPlug = (ElroPowerPlug) execution.getVariable("plug");

		System.out.println("## camunda: fire event to plug (id = "
				+ elroPowerPlug.getId() + ") to switch requested state "
				+ elroPowerPlug.isLastKnownState() + " ##");

		// TODO finalize impl

		elroPowerPlugService.setState(elroPowerPlug.getId(),
				elroPowerPlug.isLastKnownState());

	}

}
