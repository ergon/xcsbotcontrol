package ch.ergon.xcsbotcontrol.api;

public class XcodeCredentials {
	private final String username;
	private final String password;

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public XcodeCredentials(String username, String password) {
		this.username = username;
		this.password = password;
	}


}
