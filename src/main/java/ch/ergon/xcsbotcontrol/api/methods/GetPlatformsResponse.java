package ch.ergon.xcsbotcontrol.api.methods;

import java.util.List;

import ch.ergon.xcsbotcontrol.api.model.DevicePlatform;
import ch.ergon.xcsbotcontrol.gson.GsonFactory;

public class GetPlatformsResponse {
	private int count;
	private List<DevicePlatform> results;

	public static GetPlatformsResponse fromJson(String json) {
		return GsonFactory.defaultGson().fromJson(json, GetPlatformsResponse.class);
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<DevicePlatform> getResults() {
		return results;
	}

	public void setResults(List<DevicePlatform> results) {
		this.results = results;
	}
}
