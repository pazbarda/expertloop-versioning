package com.expertloop.common.operations;

import com.expertloop.users.User;
import com.expertloop.users.UserLevel;

public abstract class AbstractRestrictedOperation<T> implements Operation<T>{
	
	public AbstractRestrictedOperation(UserLevel minimumLevel, User user) throws RestrictedOperationException {
		super();
		if (!minimumLevel.userClearance(user)) {
			throw new RestrictedOperationException(this);
		}
	}
	
	public abstract String getOperationDescription();
}
