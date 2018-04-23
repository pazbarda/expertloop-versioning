package com.expertloop.versioning.actionflow.flowsteps;

import java.util.HashMap;
import java.util.Map;

public class YesNoDecisionFlowStep extends DecisionFlowStep {
	
	public static final int YES_PORT = 1;
	public static final int NO_PORT = 0;
	
	public YesNoDecisionFlowStep(String name, String description) {
		super(name, description, getYesNoPortDescriptionMapping());
	}
	
	private static Map<Integer, String> getYesNoPortDescriptionMapping()
	{
		Map<Integer, String> ret = new HashMap<>();
		ret.put(Integer.valueOf(0), "No");
		ret.put(Integer.valueOf(1), "Yes");
		return ret;
	}

}
