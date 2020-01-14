package ch.ergon.xcsbotcontrol.gson;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class BooleanTypeAdapter implements JsonDeserializer<Boolean> {
	@Override
	public Boolean deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		try {
			return json.getAsBoolean();
		} catch (ClassCastException e) {
			int code = json.getAsInt();
			return code == 0 ? false : code == 1 ? true : null;
		}
	}
}
