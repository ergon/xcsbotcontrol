package ch.ergon.xcsbotcontrol.api.model;

class Trigger {
	protected String name = "";
	protected TriggerPhase phase = TriggerPhase.BEFORE;
	protected TriggerType type = TriggerType.SCRIPT;
	protected String scriptBody = "";
	protected EmailConfiguration emailConfiguration;
	// 'phase' set to AFTER must specify Trigger property 'conditions'.
	protected TriggerConditions conditions;

	@Override
	public String toString() {
		return "Trigger{" +
				"name='" + name + '\'' +
				", phase=" + phase +
				", type=" + type +
				", scriptBody=\"" + scriptBody.replace("\n", "\\n") + "\"" +
				", emailConfiguration=" + emailConfiguration +
				", conditions=" + conditions +
				'}';
	}
}
