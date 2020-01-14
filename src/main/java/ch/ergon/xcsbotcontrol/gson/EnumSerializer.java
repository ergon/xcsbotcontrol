package ch.ergon.xcsbotcontrol.gson;

import ch.ergon.xcsbotcontrol.api.model.SerializesToInt;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

class EnumSerializer implements JsonSerializer<SerializesToInt> {
	@Override
	public JsonElement serialize(SerializesToInt src, Type typeOfSrc, JsonSerializationContext context) {
		return new JsonPrimitive(src.getIntValue());
	}
}
