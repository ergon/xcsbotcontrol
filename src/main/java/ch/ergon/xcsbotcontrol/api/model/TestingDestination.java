package ch.ergon.xcsbotcontrol.api.model;

public enum TestingDestination implements SerializesToInt {
	IOS_AND_WATCH(0),
	MAC(7);

	private int value;

	TestingDestination(int value) {
		this.value = value;
	}

	@Override
	public int getIntValue() {
		return value;
	}
}
