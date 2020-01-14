package ch.ergon.xcsbotcontrol.api.methods;

import java.util.List;

import ch.ergon.xcsbotcontrol.gson.GsonFactory;

public class GetBotResponse {
	private int count;
	private List<GetBotContainer> results;

	public static GetBotResponse fromJson(String json) {
		return GsonFactory.defaultGson().fromJson(json, GetBotResponse.class);
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<GetBotContainer> getResults() {
		return results;
	}

	public void setResults(List<GetBotContainer> results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return "GetBotResponse{" +
				"count=" + count +
				", results=" + results +
				'}';
	}
}
