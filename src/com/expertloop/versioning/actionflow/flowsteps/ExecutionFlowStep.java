package com.expertloop.versioning.actionflow.flowsteps;

public class ExecutionFlowStep extends AbstractFlowStep {
	
	public static final int FAILURE_PORT = 0;
	public static final int SUCCESS_PORT = 1;
	private static final String FAILURE_PORT_DESCRIPTION = "Execution failed";
	private static final String SUCCESS_PORT_DESCRIPTION = "Execution succeeded";
	
	public ExecutionFlowStep(String name, String description) {
		super(name, description);
		addPort(FAILURE_PORT, FAILURE_PORT_DESCRIPTION);
		addPort(SUCCESS_PORT, SUCCESS_PORT_DESCRIPTION);
	}
}
