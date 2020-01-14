package ch.ergon.xcsbotcontrol.api.methods;

import ch.ergon.xcsbotcontrol.api.model.Device;
import ch.ergon.xcsbotcontrol.gson.GsonFactory;

import java.util.List;

public class GetDevicesResponse {
	private int count;
	private List<Device> results;

	public int getCount() {
		return count;
	}

	public List<Device> getResults() {
		return results;
	}

	public static GetDevicesResponse fromJson(String json) {
		return GsonFactory.defaultGson().fromJson(json, GetDevicesResponse.class);
	}
}
