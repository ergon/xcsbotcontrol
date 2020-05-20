package ch.ergon.xcsbotcontrol.api.model;

final class ProvisioningConfiguration {
	protected boolean addMissingDevicesToTeams;
	protected boolean manageCertsAndProfiles;

	@Override
	public String toString() {
		return "ProvisioningConfiguration{" +
				"addMissingDevicesToTeams=" + addMissingDevicesToTeams +
				", manageCertsAndProfiles=" + manageCertsAndProfiles +
				'}';
	}
}
