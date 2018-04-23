package com.expertloop.common.operations;

import com.expertloop.users.User;
import com.expertloop.users.UserLevel;

public class RestrictedValueAccess<T> extends AbstractRestrictedOperation<T>{

	private final T value;
	
	public RestrictedValueAccess(T value,UserLevel minimumLevel, User user) throws RestrictedOperationException {
		super(minimumLevel, user);
		this.value = value;
	}

	@Override
	public T execute() {
		return value;
	}

	@Override
	public String getOperationDescription() {
		return ("Access the restricted value " + value.toString());
	}

}
