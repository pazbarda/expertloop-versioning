package com.expertloop.versioning.actionflow;

import java.util.HashMap;
import java.util.Map;

import com.expertloop.common.Consts;
import com.expertloop.common.Sealable;
import com.expertloop.common.UniqueIdGenerator;
import com.expertloop.users.UserLevel;
import com.expertloop.versioning.actionflow.flowsteps.AbstractFlowStep;
import com.expertloop.versioning.actionflow.flowsteps.FlowStepPortException;

public class ActionFlowVersion implements Sealable{

	private static final String START_STEP_ALREADY_EXISTS = "Start step already exists";
	private static final String STEP_DOES_NOT_EXISTS = "Step does not exist";

	private final String name;
	private final String description;
	private final UserLevel minimumUserLevel;
	private Map<Integer, AbstractFlowStep> flowMap = new HashMap<>();
	private int startStepId = Consts.INVALID_ID;
	private UniqueIdGenerator stepIdGenerator = new UniqueIdGenerator();
	private boolean sealed;
	
	public ActionFlowVersion(String name, String description, UserLevel minimumUserLevel, boolean sealed) {
		super();
		this.name = name;
		this.description = description;
		this.minimumUserLevel = minimumUserLevel;
		this.sealed = sealed;
	}

	public ActionFlowVersion(String name, String description, UserLevel minimumUserLevel) {
		this(name, description, minimumUserLevel, false);
	}
	
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public UserLevel getMinimumProfessionalLevel() {
		return minimumUserLevel;
	}

	public int getStartStepId() {
		return startStepId;
	}

	public AbstractFlowStep getStep(int stepId) throws ActionFlowException {
		AbstractFlowStep step = flowMap.get(Integer.valueOf(stepId));
		if (null == step) {
			throw new ActionFlowException(name, STEP_DOES_NOT_EXISTS);
		}
		return step;
	}

	public int getNextStepId(int stepId, int port) throws ActionFlowException {
		AbstractFlowStep step = flowMap.get(Integer.valueOf(stepId));
		if (null == step) {
			throw new ActionFlowException(name, STEP_DOES_NOT_EXISTS);
		}
		try {
			return step.getNextStepIdOnPort(port);
		} catch (FlowStepPortException e) {
			throw new ActionFlowException(name, e.getMessage());
		}
	}

	public int addFlowStep(int prevStepId, int prevStepPort, AbstractFlowStep newStep) throws ActionFlowException {
		validateUnsealed();
		int newStepId = stepIdGenerator.getNewId();
		newStep.setId(newStepId);
		AbstractFlowStep prevStep = getStep(prevStepId);
		prevStep.setNextStepIdOnPort(prevStepPort, newStepId);
		flowMap.put(Integer.valueOf(newStepId), newStep);
		return newStepId;
	}
	
	public void linkExistingSteps(int sourceStepId, int sourceStepPort, int targetStepId) throws ActionFlowException {
		validateUnsealed();
		getStep(sourceStepId).setNextStepIdOnPort(sourceStepPort, targetStepId);
	}

	public int addFlowStartStep(AbstractFlowStep startStep) throws ActionFlowException {
		validateUnsealed();
		if (Consts.INVALID_ID != startStepId) {
			throw new ActionFlowException(name, START_STEP_ALREADY_EXISTS);
		}
		startStepId = stepIdGenerator.getNewId();
		startStep.setId(startStepId);
		flowMap.put(startStepId, startStep);
		return startStepId;
	}

	@Override
	public void seal() {
		sealed = true;
	}

	@Override
	public boolean isSealed() {
		return sealed;
	}
	
	private void validateUnsealed() throws ActionFlowException {
		if (sealed) {
			throw new ActionFlowException(name, "action flow version sealed for modification");
		}
	}
}
