package com.opitz.bpmEngine.devices.engine.user.userstatus;

import org.camunda.bpm.application.ProcessApplication;
import org.camunda.bpm.application.impl.ServletProcessApplication;

/**
 * User: Pascal
 * Date: 05.09.13
 * Time: 18:05
 */

@ProcessApplication("User Status App")
public class UserStatusApplication extends ServletProcessApplication {


    public String helloWorldProcess(){
        return "Hello World!";
    }

}
