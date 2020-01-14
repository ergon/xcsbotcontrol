package ch.ergon.xcsbotcontrol.api.model;

public enum CodeCoverage implements SerializesToInt {
	USE_SCHEME_SETTING(2),
	ENABLED(1),
	DISABLED(0);

	private int value;

	CodeCoverage(int value) {
		this.value = value;
	}

	@Override
	public int getIntValue() {
		return value;
	}
}
