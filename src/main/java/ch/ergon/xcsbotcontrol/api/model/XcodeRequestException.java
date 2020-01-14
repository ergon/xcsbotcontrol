package ch.ergon.xcsbotcontrol.api.model;

public class XcodeRequestException extends RuntimeException {
	private static final long serialVersionUID = -4096346807839563111L;

	public XcodeRequestException(String message) {
		super(message);
	}

	public XcodeRequestException(String message, Throwable t) {
		super(message, t);
	}
}
