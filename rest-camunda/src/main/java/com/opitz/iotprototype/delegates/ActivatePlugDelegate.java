package com.opitz.iotprototype.delegates;

import com.opitz.iotprototype.services.ElroPowerPlugService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: Pascal
 * Date: 10.02.14
 * Time: 15:39
 */
@Service
public class ActivatePlugDelegate implements JavaDelegate {

    @Autowired
    ElroPowerPlugService elroPowerPlugService;
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("Camunda delegate: setting plug (" + execution.getVariable("plugId") + ") to state on");

        elroPowerPlugService.setState((Integer)execution.getVariable("plugId"), true);
    }
}
