package com.expertloop.versioning.actionflow.flowsteps;

public class FinalFlowStep extends AbstractFlowStep{

	public FinalFlowStep(String name, String description) {
		super(name, description);
	}

	@Override
	public boolean isEnd() {
		return true;
	}
}
