package com.expertloop.dao.versioning;

import java.util.Map;

import com.expertloop.dao.DaoException;
import com.expertloop.versioning.actionflow.ActionFlow;
import com.expertloop.versioning.actionflow.ActionFlowException;
import com.expertloop.versioning.actionflow.ActionFlowVersion;
import com.expertloop.versioning.actionflow.flowsteps.AbstractFlowStep;

public interface VersioningDao {
	
	public int persistNewActionFlow(ActionFlow actionFlow);
	
	public int persistNewActionFlowVersion(int actionFlowId, int parentActionFlowVersionId ,ActionFlowVersion actionFlowVersion) throws DaoException;
	
	public void persistExistingActionFlowVersion(int actionFlowId, int actionFlowVersionId ,ActionFlowVersion actionFlowVersion)  throws DaoException;
	
	public Map<Integer, String> getIdToActionFlowNameMap();
	
	public Map<Integer, String> getIdToActionFlowVersionNameMap(int actionFlowId) throws DaoException;

	public ActionFlow getActionFlow(int actionFlowId) throws DaoException;
	
	public ActionFlowVersion getActionFlowVersion(int actionFlowId, int actionFlowVersionId) throws DaoException, ActionFlowException;
	
	public AbstractFlowStep getActionFlowStep(int actionFlowId, int actionFlowVersionId, int stepId) throws DaoException;
	
	public void lockActionFlowVersion(int actionFlowId, int actionFlowVersionId) throws DaoException;
	
	public void unlockActionFlowVersion(int actionFlowId, int actionFlowVersionId) throws DaoException;
	
	public void sealActionFlowVersion(int actionFlowId, int actionFlowVersionId) throws DaoException;
}
