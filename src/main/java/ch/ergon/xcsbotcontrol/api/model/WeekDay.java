package ch.ergon.xcsbotcontrol.api.model;

public enum WeekDay implements SerializesToInt {
	NONE(0),
	MONDAY(1),
	TUESDAY(2),
	WEDNESDAY(3),
	THURSDAY(4),
	FRIDAY(5),
	SATURDAY(6),
	SUNDAY(7);

	private int value;

	WeekDay(int value) {
		this.value = value;
	}

	@Override
	public int getIntValue() {
		return value;
	}
}
