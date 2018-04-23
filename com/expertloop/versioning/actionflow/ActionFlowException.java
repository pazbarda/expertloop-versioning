package com.expertloop.versioning.actionflow;

public class ActionFlowException extends Exception{
	
	/**
	 * TODO - doc
	 */
	private static final long serialVersionUID = 744216619669677817L;
	private final String flowName;

	public ActionFlowException(String flowName, String message) {
		super(flowName + ": " + message);
		this.flowName = flowName;
	}

	public String getFlowName() {
		return flowName;
	}
}
