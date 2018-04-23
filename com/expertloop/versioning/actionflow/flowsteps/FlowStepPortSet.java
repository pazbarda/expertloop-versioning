package com.expertloop.versioning.actionflow.flowsteps;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.expertloop.common.Consts;

class FlowStepPortSet {
	
	private final static String PORT_DOES_NOT_EXIST = "port does not exist";
	
	private Map<Integer, StepIdAndDescription> portToStepIdAndDescription = new HashMap<>();
			
	public void add(int port, String description) {
		portToStepIdAndDescription.put(Integer.valueOf(port), new StepIdAndDescription(description));
	}
	
	public void remove(int port) {
		portToStepIdAndDescription.remove(Integer.valueOf(port));
	}
	
	public boolean isValidPort(int port) {
		return portToStepIdAndDescription.containsKey(Integer.valueOf(port));
	}
	
	public Set<Integer> getAllPorts() {
		return portToStepIdAndDescription.keySet();
	}
	
	public void setNextStepIdOnPort(int port, int stepId) {
		portToStepIdAndDescription.get(Integer.valueOf(port)).setStepId(stepId);
	}
	
	public int getStepIdOnPort(int port) throws FlowStepPortException {
		if (isValidPort(port)) {
			return portToStepIdAndDescription.get(Integer.valueOf(port)).getStepId();
		}
		throw new FlowStepPortException(port, PORT_DOES_NOT_EXIST);
	}
	
	public String getDescriptionOfPort(int port) throws FlowStepPortException {
		if (isValidPort(port)) {
			return portToStepIdAndDescription.get(Integer.valueOf(port)).getDescription();
		}
		throw new FlowStepPortException(port, PORT_DOES_NOT_EXIST);
	}
	
	private class StepIdAndDescription {
		private int stepId;
		private final String description;
		
		public StepIdAndDescription(String description) {
			super();
			this.stepId = Consts.INVALID_ID;
			this.description = description;
		}
		
		public void setStepId(int stepId) {
			this.stepId = stepId;
		}

		public int getStepId() {
			return stepId;
		}

		public String getDescription() {
			return description;
		}
	}
}
