package com.expertloop.users;

// TODO - split the user level concept from 1 dimension into multiple dimensions: Administration, Versioning, Reviewing, Execution, Authoring, etc..
public enum UserLevel implements Comparable<UserLevel>{
	MINIMUM_LEVEL_USER(-100), // TODO - remove this hack when user level is split into multiple dimensions
	MINIMUM_READ_LEVEL_USER(-1), // TODO - remove this hack when user level is split into multiple dimensions
	END_USER(0),
	REPRESENTATIVE(1), REPRESENTATIVE_MANAGER(2),
	PROFESSIONAL_L1(3), PROFESSIONAL_L2(4), PROFESSIONAL_L3(5),
	EXPERT(6), EXPERT_AUTHOR(8), PRINCIPAL(9),
	MAXIMUM_LEVEL_USER(100); // TODO - remove this hack when user level is split into multiple dimensions
	
	private final int rank;
	
	private UserLevel(int rank) {
		this.rank = rank;
	}

	public int getRank() {
		return rank;
	}
	
	public boolean userClearance(User user) {
		return user.getUserLevel().compareTo(this)>=0;
	}
}
