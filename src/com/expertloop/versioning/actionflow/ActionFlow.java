package com.expertloop.versioning.actionflow;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.expertloop.common.Consts;
import com.expertloop.common.UniqueIdGenerator;

public class ActionFlow {
	
	private static final String VERSION_DOES_NOT_EXIST = "version does not exist";
	private static final String VERSION_IS_SEALED = "version is sealed for changes";
	
	private final String name;
	private final String description;
	private final UniqueIdGenerator versionIdGenerator = new  UniqueIdGenerator();
	private Map<Integer, VersionData> versionsMap = new HashMap<>();
	private final int originalVersionId;
	
	public ActionFlow(String name, String description, ActionFlowVersion originalVersion) {
		this.name = name;
		this.description = description;
		this.originalVersionId = versionIdGenerator.getNewId();
		this.versionsMap.put(Integer.valueOf(originalVersionId), new VersionData(Consts.INVALID_ID, originalVersion));
	}
	
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public int addNewVersion(int parentVersionId, ActionFlowVersion newVersion) throws ActionFlowException {
		checkVersionId(parentVersionId);
		int id = versionIdGenerator.getNewId();
		VersionData  data = new VersionData(parentVersionId, newVersion);
		versionsMap.put(Integer.valueOf(id), data);
		versionsMap.get(Integer.valueOf(parentVersionId)).addChildVersion(id);
		return id;
	}
	
	public void updateExistingVersion(int existingVersionId, ActionFlowVersion newVersion) throws ActionFlowException{
		checkVersionId(existingVersionId);
		ActionFlowVersion version = versionsMap.get(Integer.valueOf(existingVersionId)).getVersion();
		if (version.isSealed()) {
			throw new ActionFlowException(name, VERSION_IS_SEALED);
		}
		versionsMap.get(Integer.valueOf(existingVersionId)).updateVersion(newVersion);
	}
	
	public Set<Integer> getChildVersionIds(int versionId) throws ActionFlowException {
		checkVersionId(versionId);
		return versionsMap.get(Integer.valueOf(versionId)).getChildVersionIds();
	}
	
	public int getOriginalVersionId() {
		return originalVersionId;
	}
	
	public int getLatestVersionId() {
		// TODO - use date of creation instead of id
		return Collections.max(versionsMap.keySet());
	}
	
	public int getParentVersionId(int versionId) throws ActionFlowException {
		checkVersionId(versionId);
		return versionsMap.get(Integer.valueOf(versionId)).getParentVersionId();
	}
	
	public ActionFlowVersion getVersion(int versionId) throws ActionFlowException {
		checkVersionId(versionId);
		return versionsMap.get(Integer.valueOf(versionId)).getVersion();
	}
	
	public void sealVersion(int versionId) throws ActionFlowException {
		checkVersionId(versionId);
		versionsMap.get(Integer.valueOf(versionId)).getVersion().seal();
	}
	
	private void checkVersionId(int id) throws ActionFlowException {
		if (!versionsMap.containsKey(Integer.valueOf(id))) {
			throw new ActionFlowException(name, VERSION_DOES_NOT_EXIST + " id=" + id);
		}
	}
	
	private class VersionData {
		
		private int parentId;
		private Set<Integer> childrenIds = new HashSet<>();
		private ActionFlowVersion version;
		
		public VersionData (int parentVersionId, ActionFlowVersion version) {
			this.parentId = parentVersionId;
			this.version = version;
		}
		
		public void addChildVersion(int childVersionId) {
			childrenIds.add(Integer.valueOf(childVersionId));
		}
		
		public int getParentVersionId() {
			return parentId;
		}
		
		public Set<Integer> getChildVersionIds() {
			return childrenIds;
		}

		public ActionFlowVersion getVersion() {
			return version;
		}
		
		public void updateVersion(ActionFlowVersion newVersion) {
			this.version = newVersion;
		}
	}
}
