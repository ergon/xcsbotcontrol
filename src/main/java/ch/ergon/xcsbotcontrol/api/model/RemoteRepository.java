package ch.ergon.xcsbotcontrol.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

class RemoteRepository {
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
