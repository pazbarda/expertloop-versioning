package com.expertloop.common.attributes;

import com.expertloop.common.operations.RestrictedOperationException;
import com.expertloop.common.operations.RestrictedValueAccess;
import com.expertloop.users.User;
import com.expertloop.users.UserLevel;

public class WriteRestrictedAttribute<T> {
	
	protected final UserLevel minWriteLevel;
	protected T value;
	
	public WriteRestrictedAttribute(T initialValue, UserLevel minWriteLevel)
	{
		this.value = initialValue;
		this.minWriteLevel = minWriteLevel;
	}
	
	public void set(User user,T value) throws RestrictedOperationException
	{
		this.value = new RestrictedValueAccess<T>(value, minWriteLevel, user).execute();
	}
	
	public T get()
	{
		return value;
	}

	public UserLevel getMinWriteLevel() {
		return minWriteLevel;
	}
	
	
}
