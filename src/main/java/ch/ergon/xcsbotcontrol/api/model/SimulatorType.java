package ch.ergon.xcsbotcontrol.api.model;

import com.google.gson.annotations.SerializedName;

public enum SimulatorType {
	@SerializedName("com.apple.platform.iphonesimulator")
	IPHONE,
	@SerializedName("com.apple.platform.watchsimulator")
	WATCH,
	@SerializedName("com.apple.platform.appletvsimulator")
	TV;
}
