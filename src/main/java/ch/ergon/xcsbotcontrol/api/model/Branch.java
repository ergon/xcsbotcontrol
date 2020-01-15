package ch.ergon.xcsbotcontrol.api.model;

import com.google.gson.annotations.SerializedName;

public class Branch {
	@SerializedName("DVTSourceControlBranchIdentifierKey")
	protected String branch = "master";

	/*
	 * 4 (normal remote branch) | 5 (primary remote branch, i.e. trunk)
	 */
	@SerializedName("DVTSourceControlBranchOptionsKey")
	protected int branchOptions = 5;

	@SerializedName("DVTSourceControlWorkspaceBlueprintLocationTypeKey")
	protected String type = "DVTSourceControlBranch"; // yes, we really mean a branch

	public String getBranch() {
		return branch;
	}

	@Override
	public String toString() {
		return "Branch{" +
				"branch='" + branch + '\'' +
				", branchOptions=" + branchOptions +
				", type='" + type + '\'' +
				'}';
	}
}
