package ch.ergon.xcsbotcontrol.api.model;

import java.util.ArrayList;
import java.util.List;

final class DeviceSpecification {
	protected List<String> deviceIdentifiers = new ArrayList<>();
	protected List<DeviceFilter> filters = new ArrayList<>();

	@Override
	public String toString() {
		return "DeviceSpecification{" +
				"deviceIdentifiers=" + deviceIdentifiers +
				", filters=" + filters +
				'}';
	}
}
