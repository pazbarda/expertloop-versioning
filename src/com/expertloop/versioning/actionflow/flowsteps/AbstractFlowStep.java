package com.expertloop.versioning.actionflow.flowsteps;

import java.util.Set;

public abstract class AbstractFlowStep {
	
	protected final String name;
	protected final String description;
	protected Integer id = null;
	protected FlowStepPortSet portSet;
	protected boolean isStart = false;
	
	public AbstractFlowStep(String name, String description) {
		super();
		this.name = name;
		this.description = description;
		this.portSet = new FlowStepPortSet();
	}
	
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
	
	public void setId(int id)
	{
		if (null==this.id) {
			this.id = id;
		}
	}
	
	public int getId() {
		return id.intValue();
	}

	public boolean isStart() {
		return isStart;
	}

	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}

	public boolean isEnd() {
		return false;
	}

	public Set<Integer> getAllPorts() {
		return portSet.getAllPorts();
	}
	
	public String getPortDescription(int port) throws FlowStepPortException {
		return portSet.getDescriptionOfPort(port);
	}
	
	public void setNextStepIdOnPort(int port, int stepId) {
		portSet.setNextStepIdOnPort(port, stepId);
	}
	
	public int getNextStepIdOnPort(int port) throws FlowStepPortException {
		return portSet.getStepIdOnPort(port);
	}
	
	public void linkStepToPort(int stepId, int port) {
		portSet.setNextStepIdOnPort(port, stepId);
	}
	
	protected void addPort(int port, String description) {
		portSet.add(port, description);
	}
}
