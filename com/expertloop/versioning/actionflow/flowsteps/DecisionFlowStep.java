package com.expertloop.versioning.actionflow.flowsteps;

import java.util.Map;

public class DecisionFlowStep extends AbstractFlowStep{

	public DecisionFlowStep(String name, String description, Map<Integer, String> portToDescriptionMap) {
		super(name, description);
		for (Map.Entry<Integer, String> entry : portToDescriptionMap.entrySet()) {
			addPort(entry.getKey().intValue(), entry.getValue());
		}
	}
}
