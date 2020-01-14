package ch.ergon.xcsbotcontrol.api.model;

import com.google.common.base.Preconditions;

public class TriggerBuilder {
	private final Trigger trigger = new Trigger();
	private EmailConfigurationBuilder emailConfigurationBuilder;
	private TriggerConditionsBuilder trigggerConditionsBuilder;

	public static TriggerBuilder trigger() {
		return new TriggerBuilder();
	}

	public Trigger build() {
		if (emailConfigurationBuilder != null) {
			Preconditions.checkState(trigger.phase == TriggerPhase.AFTER, "email notifications are only allowed in postbuild.");
			trigger.emailConfiguration  = emailConfigurationBuilder.build();
		}
		if (trigggerConditionsBuilder != null) {
			trigger.conditions = trigggerConditionsBuilder.build();
		}
		return trigger;
	}

	public TriggerBuilder name(String name) {
		trigger.name = name;
		return this;
	}

	public TriggerBuilder phasePrebuild() {
		trigger.phase = TriggerPhase.BEFORE;
		return this;
	}

	public TriggerBuilder phasePostbuild(TriggerConditionsBuilder conditions) {
		trigger.phase = TriggerPhase.AFTER;
		trigggerConditionsBuilder = conditions;
		return this;
	}

	public TriggerBuilder email(EmailConfigurationBuilder emailConfiguration) {
		trigger.type = TriggerType.SEND_EMAIL_NOTIFICATION;
		emailConfigurationBuilder = emailConfiguration;
		return this;
	}

	public TriggerBuilder script(String script) {
		trigger.type = TriggerType.SCRIPT;
		trigger.scriptBody = script;
		return this;
	}
}
