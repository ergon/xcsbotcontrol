package ch.ergon.xcsbotcontrol.api.model;

public final class Toolchain {
	private String _id;
	private String _rev;
	private String path;
	private String displayName;
	private String identifier;
	private boolean signatureVerified;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String get_rev() {
		return _rev;
	}

	public void set_rev(String _rev) {
		this._rev = _rev;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public boolean isSignatureVerified() {
		return signatureVerified;
	}

	public void setSignatureVerified(boolean signatureVerified) {
		this.signatureVerified = signatureVerified;
	}

	@Override
	public String toString() {
		return "Toolchain{" +
				"_id='" + _id + '\'' +
				", _rev='" + _rev + '\'' +
				", path='" + path + '\'' +
				", displayName='" + displayName + '\'' +
				", identifier='" + identifier + '\'' +
				", signatureVerified=" + signatureVerified +
				'}';
	}
}
