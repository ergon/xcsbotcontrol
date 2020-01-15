package ch.ergon.xcsbotcontrol.api.model;

import com.google.gson.annotations.SerializedName;

class RepositoryAuthentication {
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
