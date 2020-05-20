package ch.ergon.xcsbotcontrol.api.model;

public class ProvisioningConfigurationBuilder {
	private final ProvisioningConfiguration config = new ProvisioningConfiguration();

	public static ProvisioningConfigurationBuilder provisioningConfig() {
		return new ProvisioningConfigurationBuilder();
	}

	public ProvisioningConfiguration build() {
		return config;
	}

	public ProvisioningConfigurationBuilder manageCertsAndProfiles(boolean manage) {
		config.manageCertsAndProfiles = manage;
		return this;
	}

	public ProvisioningConfigurationBuilder addMissingDevicesToTeams(boolean add) {
		config.addMissingDevicesToTeams = add;
		return this;
	}
}
