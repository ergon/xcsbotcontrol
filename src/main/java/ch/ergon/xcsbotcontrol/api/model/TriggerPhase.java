package ch.ergon.xcsbotcontrol.api.model;

public enum TriggerPhase implements SerializesToInt {
	BEFORE(1),
	AFTER(2);

	private int value;

	TriggerPhase(int value) {
		this.value = value;
	}

	@Override
	public int getIntValue() {
		return value;
	}
}
