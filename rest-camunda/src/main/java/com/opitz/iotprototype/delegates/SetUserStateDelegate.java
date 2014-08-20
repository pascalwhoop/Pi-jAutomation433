package com.opitz.iotprototype.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

import com.opitz.iotprototype.entities.User;
import com.opitz.iotprototype.entities.UserState;
import com.opitz.iotprototype.services.UserService;

public class SetUserStateDelegate implements JavaDelegate {

	@Autowired
	private UserService userService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		System.out.println("## camunda: set user state ##");

		String username = (String) execution.getVariable("username");
		User user = userService.load(username);

		if (user != null) {
			String state = (String) execution.getVariable("state");
			user.setState(UserState.valueOf(state.toUpperCase()));
			userService.save(user);

			System.out.println("## camunda: user state saved ##");
		} else {
			System.out.println("## camunda: unknown user ##");
		}

	}

}
