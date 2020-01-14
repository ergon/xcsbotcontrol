package ch.ergon.xcsbotcontrol.api.model;

public enum ScheduleInterval implements SerializesToInt {
	NONE(0),
	HOURLY(1),
	DAILY(2),
	WEEKLY(3);

	private int value;

	ScheduleInterval(int value) {
		this.value = value;
	}

	@Override
	public int getIntValue() {
		return value;
	}
}
