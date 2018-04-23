package com.expertloop.dao.versioning;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.expertloop.common.Consts;
import com.expertloop.common.UniqueIdGenerator;
import com.expertloop.dao.DaoException;
import com.expertloop.users.UserLevel;
import com.expertloop.versioning.actionflow.ActionFlow;
import com.expertloop.versioning.actionflow.ActionFlowException;
import com.expertloop.versioning.actionflow.ActionFlowVersion;
import com.expertloop.versioning.actionflow.flowsteps.AbstractFlowStep;
import com.expertloop.versioning.actionflow.flowsteps.ExecutionFlowStep;
import com.expertloop.versioning.actionflow.flowsteps.FinalFlowStep;
import com.expertloop.versioning.actionflow.flowsteps.YesNoDecisionFlowStep;

public class VersioningDaoMock implements VersioningDao {
	
	private static final String ACTION_FLOW_NOT_PERSISTED = "action flow not persisted";
	private static final String ACTION_FLOW_VERSION_NOT_PERSISTED = "action flow version not persisted";
	private static final String PARENT_ACTION_FLOW_VERSION_NOT_PERSISTED = "parent action flow version not persisted";
	private static final String ACTION_FLOW_VERSION_LOCKED = "action flow version is locked";
	private static final String ACTION_FLOW_STEP_NOT_PERSISTED = "action flow version is locked";
	
	private UniqueIdGenerator idGenerator = new UniqueIdGenerator();
	private Map<Integer, ActionFlow> idToActionFlowMap = new HashMap<>();
	private Map<Integer, Set<Integer>> actionFlowIdToLockedVersionsMap = new HashMap<Integer, Set<Integer>>();
	
	private static VersioningDaoMock instance = null;
	
	private VersioningDaoMock() throws ActionFlowException {
		createInitialActionFlows();
	}
	
	public static VersioningDaoMock getInstance() throws DaoException
	{
		if (null==instance) {
			try {
				instance = new VersioningDaoMock();
			} catch (ActionFlowException e) {
				throw new DaoException(e.getMessage());
			}
		}
		return instance;
	}
	
	@Override
	public int persistNewActionFlow(ActionFlow actionFlow) {
		int newId = idGenerator.getNewId();
		idToActionFlowMap.put(newId, actionFlow);
		return newId;
	}
	
	@Override
	public int persistNewActionFlowVersion(int actionFlowId,  int parentActionFlowVersionId, ActionFlowVersion actionFlowVersion) throws DaoException {
		ActionFlow actionFlow = idToActionFlowMap.get(Integer.valueOf(actionFlowId));
		if (null==actionFlow) {
			throw new DaoException(ACTION_FLOW_NOT_PERSISTED);
		}
		int newVersionId = Consts.INVALID_ID;
		try {
			newVersionId = actionFlow.addNewVersion(parentActionFlowVersionId, actionFlowVersion);
		} catch (ActionFlowException e) {
			throw new DaoException(PARENT_ACTION_FLOW_VERSION_NOT_PERSISTED + ": " + e.getMessage());
		}
		return newVersionId;
	}

	@Override
	public void persistExistingActionFlowVersion(int actionFlowId, int actionFlowVersionId,
			ActionFlowVersion actionFlowVersion) throws DaoException {
		if (isVersionLocked(actionFlowId, actionFlowVersionId)) {
			throw new DaoException(ACTION_FLOW_VERSION_LOCKED);
		}
		ActionFlow actionFlow = idToActionFlowMap.get(Integer.valueOf(actionFlowId));
		if (null==actionFlow) {
			throw new DaoException(ACTION_FLOW_NOT_PERSISTED);
		}
		try {
			actionFlow.updateExistingVersion(actionFlowVersionId, actionFlowVersion);
		} catch (ActionFlowException e) {
			throw new DaoException(ACTION_FLOW_VERSION_NOT_PERSISTED + ": " + e.getMessage());
		}
	}
	
	@Override
	public Map<Integer, String> getIdToActionFlowNameMap() {
		Map<Integer, String> ret = new HashMap<>();
		for (Map.Entry<Integer, ActionFlow> entry : idToActionFlowMap.entrySet()) {
			ret.put(entry.getKey(), entry.getValue().getName());
		}
		return ret;
	}
	
	@Override
	public Map<Integer, String> getIdToActionFlowVersionNameMap(int actionFlowId) throws DaoException {
		ActionFlow actionFlow = idToActionFlowMap.get(Integer.valueOf(actionFlowId));
		if (null==actionFlow) {
			throw new DaoException(ACTION_FLOW_NOT_PERSISTED);
		}
		Map<Integer, String> ret = new HashMap<>();
		try {
			ret = getChildVersionIdToVersionNameMap(actionFlow, actionFlow.getOriginalVersionId());
		} catch (ActionFlowException e) {
			throw new DaoException(e.getMessage());
		}
		return ret;
	}
	
	@Override
	public ActionFlow getActionFlow(int actionFlowId) throws DaoException {
		ActionFlow ret = idToActionFlowMap.get(Integer.valueOf(actionFlowId));
		if (null==ret) {
			throw new DaoException(ACTION_FLOW_NOT_PERSISTED);
		}
		return ret;
	}

	@Override
	public ActionFlowVersion getActionFlowVersion(int actionFlowId, int actionFlowVersionId) throws DaoException, ActionFlowException {
		if (!idToActionFlowMap.containsKey(Integer.valueOf(actionFlowId))) {
			throw new DaoException(ACTION_FLOW_NOT_PERSISTED);
		}
		return idToActionFlowMap.get(Integer.valueOf(actionFlowId)).getVersion(actionFlowVersionId);
	}
	
	@Override
	public AbstractFlowStep getActionFlowStep(int actionFlowId, int actionFlowVersionId, int stepId) throws DaoException {
		ActionFlow actionFlow = idToActionFlowMap.get(Integer.valueOf(actionFlowId));
		if (null==actionFlow) {
			throw new DaoException(ACTION_FLOW_NOT_PERSISTED);
		}
		ActionFlowVersion actionFlowVersion = null;
		try {
			actionFlowVersion = idToActionFlowMap.get(Integer.valueOf(actionFlowId)).getVersion(actionFlowVersionId);
		} catch (ActionFlowException e) {
			throw new DaoException(ACTION_FLOW_VERSION_NOT_PERSISTED + ": " + e.getMessage());
		}
		AbstractFlowStep ret = null;
		try {
			ret = actionFlowVersion.getStep(stepId);
		} catch (ActionFlowException e) {
			throw new DaoException(ACTION_FLOW_STEP_NOT_PERSISTED + ": " + e.getMessage());
		}
		return ret;
	}

	@Override
	public void lockActionFlowVersion(int actionFlowId, int actionFlowVersionId) throws DaoException {
		if (!idToActionFlowMap.containsKey(actionFlowId));
	}

	@Override
	public void unlockActionFlowVersion(int actionFlowId, int actionFlowVersionId) throws DaoException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void sealActionFlowVersion(int actionFlowId, int actionFlowVersionId) throws DaoException {
		ActionFlow actionFlow = idToActionFlowMap.get(Integer.valueOf(actionFlowId));
		if (null==actionFlow) {
			throw new DaoException(ACTION_FLOW_NOT_PERSISTED);
		}
		try {
			actionFlow.sealVersion(actionFlowVersionId);
		} catch (ActionFlowException e) {
			throw new DaoException(ACTION_FLOW_VERSION_NOT_PERSISTED);
		}
	}

	private Map<Integer, String> getChildVersionIdToVersionNameMap(ActionFlow actionFlow, int actionFlowVersionId) throws ActionFlowException {
		Map<Integer, String> ret = new HashMap<>();
		ret.put(Integer.valueOf(actionFlowVersionId), actionFlow.getVersion(actionFlowVersionId).getName());
		Set<Integer> childVersionIds = actionFlow.getChildVersionIds(actionFlowVersionId);
		for (Integer childVersionId : childVersionIds) {
			ret.putAll(getChildVersionIdToVersionNameMap(actionFlow, childVersionId));
		}
		return ret;
	}
	
	private boolean isVersionLocked(int actionFlowId, int actionFlowVersionId) {
		Set<Integer> lockedVersions = actionFlowIdToLockedVersionsMap.get(Integer.valueOf(actionFlowId));
		if (null==lockedVersions) {
			return false;
		}
		if (!lockedVersions.contains(Integer.valueOf(actionFlowVersionId))) {
			return false;
		}
		return true;
	}
	
	private void createInitialActionFlows() throws ActionFlowException {
		
		/*	SAMPLE FLOW
		 * 
		 * V[Execution-Start]
		 * s)----------------->V[Decision]
		 * |						y)------------>V[Execution-Yes]
		 * |						|						s)----------->V[Final-Yes-Succeed]
		 * |						|						f)----------->V(GF)
		 * |						n)------------>V[Execution-No]
		 * |										s)----------->V[Final-No-Succeed]
		 * |										f)----------->V(GF)
		 * f)-------->V(GF)[Final- Global Fail]
		 */
		
		ActionFlowVersion V = new ActionFlowVersion("Sample Action Flow version", "This is a sample action flow version", UserLevel.END_USER);
		int startStepId = V.addFlowStartStep(new ExecutionFlowStep("Execution-Start", "Start execution"));
		int decisionStepId = V.addFlowStep(startStepId, ExecutionFlowStep.SUCCESS_PORT, new YesNoDecisionFlowStep("Decision", "Decision step"));
		int executionYesStepId = V.addFlowStep(decisionStepId, YesNoDecisionFlowStep.YES_PORT, new ExecutionFlowStep("Execution-Yes", "Execution on Yes"));
		int executionNoStepId = V.addFlowStep(decisionStepId, YesNoDecisionFlowStep.NO_PORT, new ExecutionFlowStep("Execution-Yes", "Execution on Yes"));
		@SuppressWarnings("unused")
		int finalYesSucceedStepId = V.addFlowStep(executionYesStepId, ExecutionFlowStep.SUCCESS_PORT, new FinalFlowStep("Final-Yes-Succeed", "Final step on Decision:Yes and Execution:Succeed"));
		@SuppressWarnings("unused")
		int finalNoSucceedStepId = V.addFlowStep(executionNoStepId, ExecutionFlowStep.SUCCESS_PORT, new FinalFlowStep("Final-No-Succeed", "Final step on Decision:No and Execution:Succeed"));
		int globalFailStepId = V.addFlowStep(startStepId, ExecutionFlowStep.FAILURE_PORT, new FinalFlowStep("GlobalFail", "Flow general failure"));
		V.linkExistingSteps(executionYesStepId, ExecutionFlowStep.FAILURE_PORT, globalFailStepId);
		V.linkExistingSteps(executionNoStepId, ExecutionFlowStep.FAILURE_PORT, globalFailStepId);
		
		ActionFlow F = new ActionFlow("Sample Action Flow", "This is a sample action flow", V);
		
		int newId = idGenerator.getNewId();
		idToActionFlowMap.put(newId, F);
	}
}
