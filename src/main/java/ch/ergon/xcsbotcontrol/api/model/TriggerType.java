package ch.ergon.xcsbotcontrol.api.model;

public enum TriggerType implements SerializesToInt {
	SCRIPT(1),
	SEND_EMAIL_NOTIFICATION(2);

	private int value;

	TriggerType(int value) {
		this.value = value;
	}

	@Override
	public int getIntValue() {
		return value;
	}
}
