package com.opitz.devices.delegates;

import com.opitz.devices.services.ElroPowerPlugService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: cor
 * Date: 21.01.14
 * Time: 13:20
 * To change this template use File | Settings | File Templates.
 */
@Service
public class DeactivatePlugDelegate implements JavaDelegate {

    @Autowired
    ElroPowerPlugService elroPowerPlugService;
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("Camunda delegate: setting plug (" + execution.getVariable("plugId") + ") to state off");



        elroPowerPlugService.setState((Integer)execution.getVariable("plugId"), false);

    }
}
