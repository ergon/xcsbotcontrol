package ch.ergon.xcsbotcontrol.api.model;

import com.google.gson.annotations.SerializedName;

public final class DevicePlatform {
	@SerializedName("_id")
	private String id;
	private String displayName;
	private String version;
	private PlatformType identifier;
	private SimulatorType simulatorIdentifier;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public PlatformType getIdentifier() {
		return identifier;
	}

	public void setIdentifier(PlatformType identifier) {
		this.identifier = identifier;
	}

	public SimulatorType getSimulatorIdentifier() {
		return simulatorIdentifier;
	}

	public void setSimulatorIdentifier(SimulatorType simulatorIdentifier) {
		this.simulatorIdentifier = simulatorIdentifier;
	}

	@Override
	public String toString() {
		return "DevicePlatform{" +
				"id='" + id + '\'' +
				", displayName='" + displayName + '\'' +
				", version='" + version + '\'' +
				", identifier=" + identifier +
				", simulatorIdentifier=" + simulatorIdentifier +
				'}';
	}
}
