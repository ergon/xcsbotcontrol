package ch.ergon.xcsbotcontrol.api.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.gson.annotations.SerializedName;

public class SourceControlBlueprint {
	@SerializedName("DVTSourceControlWorkspaceBlueprintNameKey")
	protected String blueprintName;

	/*
	 * Remotes, their credentials, their branch and more
	 */

	@SerializedName("DVTSourceControlWorkspaceBlueprintPrimaryRemoteRepositoryKey")
	protected String primaryRepoId; // Main repo when using submodules

	@SerializedName("DVTSourceControlWorkspaceBlueprintRemoteRepositoriesKey")
	protected List<RemoteRepository> remoteRepository = new ArrayList<>();

	@SerializedName("DVTSourceControlWorkspaceBlueprintRemoteRepositoryAuthenticationStrategiesKey")
	protected Map<String, RepositoryAuthentication> authentication = new HashMap<>(); // repoId -> Authentication

	@SerializedName("DVTSourceControlWorkspaceBlueprintLocationsKey")
	protected Map<String, Branch> branch = new HashMap<>(); // repoId -> Branch

	// Relative path in your repo to the working copy
	@SerializedName("DVTSourceControlWorkspaceBlueprintWorkingCopyPathsKey")
	protected Map<String, String> workingCopyPath = new HashMap<>(); // repoId -> working copy path

	// Relative path in workingCopyPath to the Xcode project/workspace
	@SerializedName("DVTSourceControlWorkspaceBlueprintRelativePathToProjectKey")
	protected String relativePathToProject;

	/*
	 * Magic undocumented fields
	 */

	@SerializedName("DVTSourceControlWorkspaceBlueprintVersion")
	protected int version = 203; // Magic undocumented mandatory field

	@SerializedName("DVTSourceControlWorkspaceBlueprintIdentifierKey")
	protected String uuid = UUID.randomUUID().toString();

	@SerializedName("DVTSourceControlWorkspaceBlueprintWorkingCopyStatesKey")
	protected Map<String, BigDecimal> workingCopyStates = new HashMap<>();

	public String getPrimaryRepoId() {
		return primaryRepoId;
	}

	public Map<String, Branch> getBranch() {
		return branch;
	}

	@Override
	public String toString() {
		return "SourceControlBlueprint{" +
				"blueprintName='" + blueprintName + '\'' +
				", primaryRepoId='" + primaryRepoId + '\'' +
				", remoteRepository=" + remoteRepository +
				", authentication=" + authentication +
				", branch=" + branch +
				", workingCopyPath=" + workingCopyPath +
				", relativePathToProject='" + relativePathToProject + '\'' +
				", version=" + version +
				", uuid='" + uuid + '\'' +
				", workingCopyStates=" + workingCopyStates +
				'}';
	}
}
