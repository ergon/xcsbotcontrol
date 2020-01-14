package ch.ergon.xcsbotcontrol.api.model;

class DevicePlatformBuilder {
	private final DevicePlatform platform = new DevicePlatform();

	public static DevicePlatformBuilder platform() {
		return new DevicePlatformBuilder();
	}

	public DevicePlatform build() {
		return platform;
	}

	public DevicePlatformBuilder displayName(String name) {
		platform.setDisplayName(name);
		return this;
	}

	public DevicePlatformBuilder version(String version) {
		platform.setVersion(version);
		return this;
	}

	public DevicePlatformBuilder identifier(PlatformType type) {
		platform.setIdentifier(type);
		return this;
	}

	public DevicePlatformBuilder simulatorIdentifier(SimulatorType type) {
		platform.setSimulatorIdentifier(type);
		return this;
	}

	public DevicePlatformBuilder identifier(String id) {
		platform.setId(id);
		return this;
	}
}
