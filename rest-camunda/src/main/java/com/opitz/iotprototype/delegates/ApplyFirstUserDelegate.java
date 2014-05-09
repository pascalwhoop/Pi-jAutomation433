package com.opitz.iotprototype.delegates;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

import com.opitz.iotprototype.entities.User;
import com.opitz.iotprototype.entities.UserState;
import com.opitz.iotprototype.services.UserService;

/**
 * Use execution variable 'username' to determine if user is first in building
 * and set result to execution variable 'isFirst'.
 * 
 * @author developer
 * 
 */
public class ApplyFirstUserDelegate implements JavaDelegate {

	@Autowired
	private UserService userService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		System.out.println("## camunda: check any user already arrived ##");

		String username = (String) execution.getVariables().get("username");

		List<User> users = userService.listAll();

		boolean isFirst = true;
		for (User user : users) {
			if ((!user.getUsername().equals(username))
			    && user.getState().equals(UserState.ONLINE)) {
				isFirst = false;
				break;
			}
		}

		System.out.println("## camunda: user is first = " + isFirst + " ##");
		execution.setVariable("isFirst", isFirst);
	}

}
