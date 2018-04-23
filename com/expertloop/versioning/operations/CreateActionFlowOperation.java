package com.expertloop.versioning.operations;

import com.expertloop.common.operations.AbstractRestrictedOperation;
import com.expertloop.common.operations.RestrictedOperationException;
import com.expertloop.users.User;
import com.expertloop.users.UserLevel;
import com.expertloop.versioning.actionflow.ActionFlow;
import com.expertloop.versioning.actionflow.ActionFlowVersion;

public class CreateActionFlowOperation extends AbstractRestrictedOperation<ActionFlow>{

	private final String name;
	private final String description;
	private final ActionFlowVersion originalVersion;
	
	public CreateActionFlowOperation(String name, String description, ActionFlowVersion originalVersion, User user) throws RestrictedOperationException {
		super(UserLevel.EXPERT_AUTHOR, user);
		this.name = name;
		this.originalVersion = originalVersion;
		this.description = description;
	}

	@Override
	public ActionFlow execute() {
		return new ActionFlow(name, description, originalVersion);
	}

	@Override
	public String getOperationDescription() {
		return "Create a new Action Flow " + name + " using the following original version + " + originalVersion.getName();
	}

}
