package ch.ergon.xcsbotcontrol.api.model;

import java.util.HashMap;
import java.util.Map;

public enum Clean implements SerializesToInt {
	NEVER(0),
	ALWAYS(1),
	ONCE_A_DAY(2),
	ONCE_A_WEEK(3);

	private static Map<Integer, Clean> typesByValue = new HashMap<>();
	static {
		for (Clean type : Clean.values()) {
			typesByValue.put(type.value, type);
		}
	}

	private int value;

	Clean(int value) {
		this.value = value;
	}

	@Override
	public int getIntValue() {
		return value;
	}

	public static Clean of(int value) {
		return typesByValue.get(value);
	}
}
