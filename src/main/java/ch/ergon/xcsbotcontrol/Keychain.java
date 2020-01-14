package ch.ergon.xcsbotcontrol;

import ch.ergon.xcsbotcontrol.api.XcodeCredentials;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Keychain {
	XcodeCredentials findItem(String name) throws IOException {
		String command = "security find-generic-password -gs " + name;
		Process child = Runtime.getRuntime().exec(command);

		try (BufferedReader out = new BufferedReader(new InputStreamReader(
				child.getInputStream()));
			 BufferedReader err = new BufferedReader(
					 new InputStreamReader(child.getErrorStream()))) {
			String user = null;
			String password = null;
			String s;

			while ((s = out.readLine()) != null) {
				if (s.matches(" *\"acct\".*")) {
					user = s.replaceAll("^.*=\"", "").replace("\"", "");
					break;
				}
			}
			s = err.readLine();
			if (s.contains("SecKeychainSearchCopyNext")) {
				throw new RuntimeException("Could not find keychain item with name " + name);
			}
			password = s.replaceAll("^.*: *\"", "").replace("\"", "");
			return new XcodeCredentials(user,  password);
		}
	}
}
