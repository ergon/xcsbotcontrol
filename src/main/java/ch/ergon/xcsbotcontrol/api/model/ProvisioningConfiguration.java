package ch.ergon.xcsbotcontrol.api.model;

final class ProvisioningConfiguration {
	protected boolean addMissingDevicesToTeam;
	protected boolean manageCertsAndProfiles;

	@Override
	public String toString() {
		return "ProvisioningConfiguration{" +
				"addMissingDevicesToTeam=" + addMissingDevicesToTeam +
				", manageCertsAndProfiles=" + manageCertsAndProfiles +
				'}';
	}
}
