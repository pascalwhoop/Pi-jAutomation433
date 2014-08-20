package com.opitz.iotprototype.delegates;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

import com.opitz.iotprototype.entities.User;
import com.opitz.iotprototype.entities.UserState;
import com.opitz.iotprototype.services.UserService;

/**
 * Use execution variable 'username' to determine if user is last in building
 * and set result to execution variable 'isLast'.
 * 
 * @author developer
 * 
 */
public class ApplyLastUserDelegate implements JavaDelegate {

	@Autowired
	private UserService userService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		System.out.println("## camunda: check user is last ##");

		String username = (String) execution.getVariables().get("username");

		List<User> users = userService.listAll();

		boolean isLast = true;
		for (User user : users) {
			if ((!user.getUsername().equals(username))
			    && user.getState().equals(UserState.ONLINE)) {
				isLast = false;
				break;
			}
		}

		System.out.println("## camunda: user is last: " + isLast + " ##");
		execution.setVariable("isLast", isLast);
	}

}
