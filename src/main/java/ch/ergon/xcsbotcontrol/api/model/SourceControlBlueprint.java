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
	protected Map<String, Authentication> authentication = new HashMap<>(); // repoId -> Authentication

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

	static class RemoteRepository {
		@SerializedName("DVTSourceControlWorkspaceBlueprintRemoteRepositoryIdentifierKey")
		protected String repoId = UUID.randomUUID().toString();

		@SerializedName("DVTSourceControlWorkspaceBlueprintRemoteRepositoryURLKey")
		protected String remoteUrl;

		@SerializedName("DVTSourceControlWorkspaceBlueprintRemoteRepositorySystemKey")
		protected String scmType = "com.apple.dt.Xcode.sourcecontrol.Git"; // fixed to GIT for now

		@SerializedName("DVTSourceControlWorkspaceBlueprintRemoteRepositoryTrustedCertFingerprintKey")
		protected String certificateFingerprint;

		@SerializedName("DVTSourceControlWorkspaceBlueprintRemoteRepositoryTrustSelfSignedCertKey")
		protected boolean trustSelfSignedCertificate = true;

		@Override
		public String toString() {
			return "RemoteRepository{" +
					"repoId='" + repoId + '\'' +
					", remoteUrl='" + remoteUrl + '\'' +
					", scmType='" + scmType + '\'' +
					", certificateFingerprint='" + certificateFingerprint + '\'' +
					", trustSelfSignedCertificate=" + trustSelfSignedCertificate +
					'}';
		}
	}

	static class Authentication {
		@SerializedName("DVTSourceControlWorkspaceBlueprintRemoteRepositoryAuthenticationTypeKey")
		protected String authenticationType = "DVTSourceControlSSHKeysAuthenticationStrategy"; // only supporting SSH Keys here

		@SerializedName("DVTSourceControlWorkspaceBlueprintRemoteRepositoryUsernameKey")
		protected String username = "git";

		@SerializedName("DVTSourceControlWorkspaceBlueprintRemoteRepositoryPasswordKey")
		protected String sshKeyPassphrase = "";

		@SerializedName("DVTSourceControlWorkspaceBlueprintRemoteRepositoryAuthenticationStrategiesKey")
		protected String sshPrivateKey; // must be base64 encoded

		@SerializedName("DVTSourceControlWorkspaceBlueprintRemoteRepositoryPublicKeyDataKey")
		protected String sshPublicKey; // must be base64 encoded optional

		@Override
		public String toString() {
			return "Authentication{" +
					"authenticationType='" + authenticationType + '\'' +
					", username='" + username + '\'' +
					", sshKeyPassphrase='" + sshKeyPassphrase + '\'' +
					", sshPrivateKey='" + sshPrivateKey + '\'' +
					", sshPublicKey='" + sshPublicKey + '\'' +
					'}';
		}
	}

	static class Branch {
		@SerializedName("DVTSourceControlBranchIdentifierKey")
		protected String branch = "master";

		/*
		 * 4 (normal remote branch) | 5 (primary remote branch, i.e. trunk)
		 */
		@SerializedName("DVTSourceControlBranchOptionsKey")
		protected int branchOptions = 5;

		@SerializedName("DVTSourceControlWorkspaceBlueprintLocationTypeKey")
		protected String type = "DVTSourceControlBranch"; // yes, we really mean a branch

		@Override
		public String toString() {
			return "Branch{" +
					"branch='" + branch + '\'' +
					", branchOptions=" + branchOptions +
					", type='" + type + '\'' +
					'}';
		}
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
