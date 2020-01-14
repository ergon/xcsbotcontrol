package ch.ergon.xcsbotcontrol.api.methods;

import ch.ergon.xcsbotcontrol.api.model.BotConfiguration;
import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.Objects;

public class CreateBotRequest {
	private final BotConfiguration configuration;
	private final Map<String, String> group;
	private final boolean requiresUpgrade;
	private final String name;
	private final int type = 1; // uncommented magic

	public CreateBotRequest(BotConfiguration configuration, Map<String, String> group, boolean requiresUpgrade, String name) {
		this.configuration = Objects.requireNonNull(configuration);
		this.group = ImmutableMap.copyOf(group);
		this.requiresUpgrade = requiresUpgrade;
		this.name = Objects.requireNonNull(name);
	}

	public BotConfiguration getConfiguration() {
		return configuration;
	}

	public Map<String, String> getGroup() {
		return group;
	}

	public boolean isRequiresUpgrade() {
		return requiresUpgrade;
	}

	public String getName() {
		return name;
	}

	public int getType() {
		return type;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		CreateBotRequest that = (CreateBotRequest) o;

		return Objects.equals(this.getConfiguration(), that.getConfiguration()) && Objects.equals(this.getGroup(), that.getGroup()) && Objects.equals(this.isRequiresUpgrade(), that.isRequiresUpgrade()) && Objects.equals(this.getName(), that.getName()) && Objects.equals(this.getType(), that.getType());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getConfiguration(), getGroup(), isRequiresUpgrade(), getName(), getType());
	}

	@Override
	public String toString() {
		return "CreateBotRequest{" +
				"configuration=" + configuration +
				", group=" + group +
				", requiresUpgrade=" + requiresUpgrade +
				", name='" + name + '\'' +
				", type=" + type +
				'}';
	}
}
