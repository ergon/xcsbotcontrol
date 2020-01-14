package ch.ergon.xcsbotcontrol.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

public class Device {
	@SerializedName("_id")
	private String id;

	private String modelCode;
	private OsVersion osVersion;
	private String modelName;
	private String name;
	private String tinyID;

	private boolean connected;
	private boolean simulator;
	private boolean supported;
	private boolean enabledForDevelopment;
	private boolean isServer;
	private boolean trusted;
	private boolean retina;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public OsVersion getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(OsVersion osVersion) {
		this.osVersion = osVersion;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTinyID() {
		return tinyID;
	}

	public void setTinyID(String tinyID) {
		this.tinyID = tinyID;
	}

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	public boolean isSimulator() {
		return simulator;
	}

	public void setSimulator(boolean simulator) {
		this.simulator = simulator;
	}

	public boolean isSupported() {
		return supported;
	}

	public void setSupported(boolean supported) {
		this.supported = supported;
	}

	public boolean isEnabledForDevelopment() {
		return enabledForDevelopment;
	}

	public void setEnabledForDevelopment(boolean enabledForDevelopment) {
		this.enabledForDevelopment = enabledForDevelopment;
	}

	public boolean isServer() {
		return isServer;
	}

	public void setServer(boolean server) {
		isServer = server;
	}

	public boolean isTrusted() {
		return trusted;
	}

	public void setTrusted(boolean trusted) {
		this.trusted = trusted;
	}

	public boolean isRetina() {
		return retina;
	}

	public void setRetina(boolean retina) {
		this.retina = retina;
	}

	public static Comparator<Device> byOsVersion() {
		return new Comparator<Device>() {
			@Override
			public int compare(Device lhs, Device rhs) {
				return lhs.osVersion.compareTo(rhs.osVersion);
			}
		};
	}

	@Override
	public String toString() {
		return "Device{" +
				"id='" + id + '\'' +
				", modelCode='" + modelCode + '\'' +
				", osVersion=" + osVersion +
				", modelName='" + modelName + '\'' +
				", name='" + name + '\'' +
				", tinyID='" + tinyID + '\'' +
				", connected=" + connected +
				", simulator=" + simulator +
				", supported=" + supported +
				", enabledForDevelopment=" + enabledForDevelopment +
				", isServer=" + isServer +
				", trusted=" + trusted +
				", retina=" + retina +
				'}';
	}
}
