package com.expertloop.web.versioning.readers.text;

import com.expertloop.versioning.actionflow.ActionFlowException;
import com.expertloop.versioning.actionflow.ActionFlowVersion;
import com.expertloop.web.versioning.readers.AbstractActionFlowStepReader;


public class TextActionFlowStepReader extends AbstractActionFlowStepReader<String>{
	
	private static final String NL = "\n";
	
	public TextActionFlowStepReader(ActionFlowVersion actionFlowVersion, int stepId) throws ActionFlowException {
		super(actionFlowVersion, stepId);
	}
	
	@Override
	public String getStepInfo() {
		return (new StringBuilder().append(step.getName()).append(NL).append(step.getDescription())).toString();
	}
}
