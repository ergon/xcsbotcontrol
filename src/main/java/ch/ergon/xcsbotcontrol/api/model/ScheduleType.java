package ch.ergon.xcsbotcontrol.api.model;

public enum ScheduleType implements SerializesToInt {
	PERIODICALLY(1),
	ON_COMMIT(2),
	MANUAL(3);

	private int value;

	ScheduleType(int value) {
		this.value = value;
	}

	@Override
	public int getIntValue() {
		return value;
	}
}
