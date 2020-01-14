package ch.ergon.xcsbotcontrol.gson;

import ch.ergon.xcsbotcontrol.api.model.OsVersion;
import ch.ergon.xcsbotcontrol.api.model.SerializesToInt;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonFactory {
	public static Gson defaultGson() {
		return defaultGsonBuilder().create();
	}

	public static Gson prettyGson() {
		GsonBuilder builder = defaultGsonBuilder();
		defaultGsonBuilder().setPrettyPrinting();
		return builder.create();
	}

	private static GsonBuilder defaultGsonBuilder() {
		GsonBuilder gsonBuilder = new GsonBuilder();

		/* Lame but true: Xcode Server does not understand "integers" but only integers.
		 * Therefore we hate to break DRY in the enums and assign them values instead of using
		 * the @SerializedName feature of GSON.
		 */
		gsonBuilder.registerTypeHierarchyAdapter(SerializesToInt.class, new EnumSerializer());
		gsonBuilder.registerTypeAdapter(Boolean.class, new BooleanTypeAdapter());
		gsonBuilder.registerTypeAdapter(OsVersion.class, new OsVersionDeserializer());
		return gsonBuilder;
	}
}
