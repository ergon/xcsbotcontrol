package ch.ergon.xcsbotcontrol.api.methods;

import ch.ergon.xcsbotcontrol.api.model.Toolchain;
import ch.ergon.xcsbotcontrol.gson.GsonFactory;

import java.util.List;

public class GetToolchainsResponse {
	private List<Toolchain> results;

	public static GetToolchainsResponse fromJson(String json) {
		return GsonFactory.defaultGson().fromJson(json, GetToolchainsResponse.class);
	}

	public List<Toolchain> getResults() {
		return results;
	}

	public void setResults(List<Toolchain> results) {
		this.results = results;
	}
}
