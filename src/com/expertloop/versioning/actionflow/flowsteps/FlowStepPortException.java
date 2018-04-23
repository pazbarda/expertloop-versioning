package com.expertloop.versioning.actionflow.flowsteps;

import com.expertloop.common.Consts;

public class FlowStepPortException extends Exception {
	
	/**
	 * TODO - doc
	 */
	private static final long serialVersionUID = -2049170637411349116L;
	
	private int flowStepId;
	private final int port;

	
	public FlowStepPortException(int flowStepId, int port, String message) {
		super(message);
		this.flowStepId = flowStepId;
		this.port = port;
	}
	
	public FlowStepPortException(int port)
	{
		this(Consts.INVALID_ID, port, Consts.EMPTY_STRING);
	}
	
	public FlowStepPortException(int port, String message) {
		this(Consts.INVALID_ID, port, message );
	}

	public int getFlowStepId() {
		return flowStepId;
	}

	public int getPort() {
		return port;
	}
}
