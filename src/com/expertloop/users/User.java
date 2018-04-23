package com.expertloop.users;

import com.expertloop.common.UniqueIdGenerator;
import com.expertloop.common.attributes.WriteRestrictedAttribute;
import com.expertloop.common.operations.RestrictedOperationException;

public class User {
	
	// user class handles its own objects' ids
	private static final UniqueIdGenerator idGenerator = new UniqueIdGenerator();
	
	private WriteRestrictedAttribute<UserLevel> userLevel;
	private WriteRestrictedAttribute<String> name;
	private final int id;
	
	public User(UserLevel userLevel, String name) {
		super();
		// TODO - REPRESENTATIVE should be replaced with a write-privileged administrative level when user level enum is split
		this.userLevel = new WriteRestrictedAttribute<UserLevel>(userLevel, UserLevel.REPRESENTATIVE);
		this.name = new WriteRestrictedAttribute<String>(name, UserLevel.REPRESENTATIVE);
		this.id = idGenerator.getNewId();
	}

	public UserLevel getUserLevel() {
		return userLevel.get();
	}

	public void setUserLevel(User accessingUser, UserLevel userLevel) throws RestrictedOperationException {		
		this.userLevel.set(accessingUser, userLevel);
	}

	public String getName(User accessingUser) throws RestrictedOperationException {
		return name.get();
	}

	public void setName(User accessingUser, String name) throws RestrictedOperationException {
		this.name.set(accessingUser, name);
	}
}
