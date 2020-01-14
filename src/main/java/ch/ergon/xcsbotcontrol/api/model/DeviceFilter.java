package ch.ergon.xcsbotcontrol.api.model;

final class DeviceFilter {
	protected DevicePlatform platform;
	protected DeviceFilterType filterType;
	protected DeviceArchitectureType architectureType;

	@Override
	public String toString() {
		return "DeviceFilter{" +
				"platform=" + platform +
				", filterType=" + filterType +
				", architectureType=" + architectureType +
				'}';
	}
}
