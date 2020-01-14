package ch.ergon.xcsbotcontrol.api.model;

public enum DeviceFilterType implements SerializesToInt {
	ALL_AVAILABLE_DEVICES_AND_SIMULATORS(0),
	ALL_DEVICES(1),
	ALL_SIMULATORS(2),
	SELECTED_DEVICES_AND_SIMULATORS(3);

	private int value;

	DeviceFilterType(int value) {
		this.value = value;
	}

	@Override
	public int getIntValue() {
		return value;
	}
}
