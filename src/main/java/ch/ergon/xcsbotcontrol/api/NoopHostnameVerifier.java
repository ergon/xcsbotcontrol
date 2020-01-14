package ch.ergon.xcsbotcontrol.api;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class NoopHostnameVerifier  implements HostnameVerifier {

	@Override
	public boolean verify(final String hostname, final SSLSession session) {
		return true;
	}

	@Override
	public final String toString() {
		return "NOP";
	}

}