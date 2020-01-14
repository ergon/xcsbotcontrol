package ch.ergon.xcsbotcontrol.gson;

import java.lang.reflect.Type;

import ch.ergon.xcsbotcontrol.api.model.OsVersion;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class OsVersionDeserializer implements JsonDeserializer<OsVersion> {
	@Override
	public OsVersion deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		String versionString = json.getAsString();
		String[] versionParts = versionString.split("\\.");
		if (versionParts.length >= 2) {
			try {
				int major = Integer.parseInt(versionParts[0]);
				int minor = Integer.parseInt(versionParts[1]);
				int revision = 0;
				if (versionParts.length > 2) {
					revision = Integer.parseInt(versionParts[2]);
				}
				return new OsVersion(major, minor, revision);
			} catch (NumberFormatException e) {
				throw new JsonParseException("Failed to parse OS Version: " + e.getMessage());
			}
		} else {
			throw new JsonParseException("Failed to parse OS Version: " + versionString);
		}
	}
}
