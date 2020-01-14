package ch.ergon.xcsbotcontrol.api.model;

import com.google.gson.annotations.SerializedName;

public enum PlatformType {
	@SerializedName("unknown")
	UNKNOWN,
	@SerializedName("com.apple.platform.iphoneos")
	IOS,
	@SerializedName("com.apple.platform.iphonesimulator")
	IOS_SIMULATOR,
	@SerializedName("com.apple.platform.macosx")
	OSX,
	@SerializedName("com.apple.platform.watchos")
	WATCHOS,
	@SerializedName("com.apple.platform.watchsimulator")
	WATCHOS_SIMULATOR,
	@SerializedName("com.apple.platform.appletvos")
	TVOS,
	@SerializedName("com.apple.platform.appletvsimulator")
	TVOS_SIMULATOR;
}
