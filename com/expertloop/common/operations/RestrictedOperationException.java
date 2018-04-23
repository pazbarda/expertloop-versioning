package com.expertloop.common.operations;

public class RestrictedOperationException extends Exception{
	/**
	 * TODO - doc
	 */
	private static final long serialVersionUID = 3245017507050053486L;
	private final AbstractRestrictedOperation<?> operation;
	
	public RestrictedOperationException(AbstractRestrictedOperation<?> operation) {
		this.operation = operation;
	}

	public AbstractRestrictedOperation<?> getOperation() {
		return operation;
	}
}
