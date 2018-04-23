package com.expertloop.web.versioning.readers;

import com.expertloop.versioning.actionflow.ActionFlowException;
import com.expertloop.versioning.actionflow.ActionFlowVersion;
import com.expertloop.versioning.actionflow.flowsteps.AbstractFlowStep;

public abstract class AbstractActionFlowStepReader<T> implements ActionFlowStepReader<T>{

	protected final ActionFlowVersion actionFlowVersion;
	protected final AbstractFlowStep step;
	
	public AbstractActionFlowStepReader(ActionFlowVersion actionFlowVersion, int stepId) throws ActionFlowException {
		super();
		this.actionFlowVersion = actionFlowVersion;
		this.step = this.actionFlowVersion.getStep(stepId);
	}
	
}
