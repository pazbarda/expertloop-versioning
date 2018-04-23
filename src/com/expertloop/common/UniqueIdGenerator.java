package com.expertloop.common;

public class UniqueIdGenerator {
	
	// TODO - ctor needs to be synchronized 
	
	private static final int OFFSET_STEP = 1000;
	private static int nextIdoffset = 1000;
	
	private int nextId;
	
	public UniqueIdGenerator()
	{
		nextId = nextIdoffset;
		nextIdoffset += OFFSET_STEP;
	}
	
	public int getNewId() {
		return nextId++;
	}
}
