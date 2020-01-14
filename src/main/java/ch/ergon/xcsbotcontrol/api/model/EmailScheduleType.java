package ch.ergon.xcsbotcontrol.api.model;

public enum EmailScheduleType implements SerializesToInt {
	AFTER_EACH_INTEGRATION(0),
	DAILY(1),
	WEEKLY(2);

	private int value;

	EmailScheduleType(int value) {
		this.value = value;
	}

	@Override
	public int getIntValue() {
		return value;
	}
}
