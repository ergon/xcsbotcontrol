package ch.ergon.xcsbotcontrol.api.model;

public enum DeviceArchitectureType implements SerializesToInt {
	UNKNOWN(-1),
	IOS_WATCH_TV_OS(0),
	OSX(1);

	private int value;

	DeviceArchitectureType(int value) {
		this.value = value;
	}

	@Override
	public int getIntValue() {
		return value;
	}
}
