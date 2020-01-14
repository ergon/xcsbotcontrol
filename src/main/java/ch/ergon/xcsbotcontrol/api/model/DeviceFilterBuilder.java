package ch.ergon.xcsbotcontrol.api.model;

public class DeviceFilterBuilder {
	private final DeviceFilter filter = new DeviceFilter();
	private DevicePlatformBuilder platformBuilder;
	private DevicePlatform platform;

	public static DeviceFilterBuilder filter() {
		return new DeviceFilterBuilder();
	}

	public DeviceFilter build() {
		if (platformBuilder != null) {
			filter.platform = platformBuilder.build();
		} else if (platform != null) {
			filter.platform = platform;
		}
		return filter;
	}

	public DeviceFilterBuilder platform(DevicePlatformBuilder platform) {
		this.platformBuilder = platform;
		return this;
	}

	public DeviceFilterBuilder platform(DevicePlatform devicePlatform) {
		this.platform = devicePlatform;
		return this;
	}

	public DeviceFilterBuilder type(DeviceFilterType type) {
		filter.filterType = type;
		return this;
	}

	public DeviceFilterBuilder architecture(DeviceArchitectureType architecture) {
		filter.architectureType = architecture;
		return this;
	}
}
