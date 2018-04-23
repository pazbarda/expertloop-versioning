package com.expertloop.versioning.operations;

import com.expertloop.common.operations.AbstractRestrictedOperation;
import com.expertloop.common.operations.RestrictedOperationException;
import com.expertloop.users.User;
import com.expertloop.users.UserLevel;
import com.expertloop.versioning.actionflow.ActionFlow;
import com.expertloop.versioning.actionflow.ActionFlowException;
import com.expertloop.versioning.actionflow.ActionFlowVersion;

public class RegisterActionFlowVersionOperation extends AbstractRestrictedOperation<Integer>{

	private final ActionFlow actionFlow;
	private final int parentVersionId;
	private final ActionFlowVersion newActionFlowVersion;
	
	public RegisterActionFlowVersionOperation(ActionFlow actionFlow, int parentVersionId, ActionFlowVersion newActionVersion ,User user) throws RestrictedOperationException {
		super(UserLevel.EXPERT, user);
		this.actionFlow = actionFlow;
		this.parentVersionId = parentVersionId;
		this.newActionFlowVersion = newActionVersion;
	}

	@Override
	public Integer execute() throws ActionFlowException {
		return actionFlow.addNewVersion(parentVersionId, newActionFlowVersion);
	}

	@Override
	public String getOperationDescription() {
		return "Register the new action flow version " + newActionFlowVersion.getName() + " under flow " + actionFlow.getName();
	}

}
