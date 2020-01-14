package ch.ergon.xcsbotcontrol.config;

public class Repository {
	private String url;
	private String sshFingerprint;
	private String branch;
	private String workingCopyPath;
	private String sshPublicKey;
	private String sshPrivateKey;
	private String sshPrivateKeyKeyChainItem;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSshFingerprint() {
		return sshFingerprint;
	}

	public void setSshFingerprint(String sshFingerprint) {
		this.sshFingerprint = sshFingerprint;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getWorkingCopyPath() {
		return workingCopyPath;
	}

	public void setWorkingCopyPath(String workingCopyPath) {
		this.workingCopyPath = workingCopyPath;
	}

	public String getSshPublicKey() {
		return sshPublicKey;
	}

	public void setSshPublicKey(String sshPublicKey) {
		this.sshPublicKey = sshPublicKey;
	}

	public String getSshPrivateKey() {
		return sshPrivateKey;
	}

	public void setSshPrivateKey(String sshPrivateKey) {
		this.sshPrivateKey = sshPrivateKey;
	}

	public String getSshPrivateKeyKeyChainItem() {
		return sshPrivateKeyKeyChainItem;
	}

	public void setSshPrivateKeyKeyChainItem(String sshPrivateKeyKeyChainItem) {
		this.sshPrivateKeyKeyChainItem = sshPrivateKeyKeyChainItem;
	}
}
