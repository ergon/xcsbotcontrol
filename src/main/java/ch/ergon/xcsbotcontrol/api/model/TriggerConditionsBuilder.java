package ch.ergon.xcsbotcontrol.api.model;

public class TriggerConditionsBuilder {
	private final TriggerConditions cond = new TriggerConditions();

	public static TriggerConditionsBuilder triggerConditions() {
		return new TriggerConditionsBuilder();
	}

	public TriggerConditions build() {
		return cond;
	}

	public TriggerConditionsBuilder warnings(boolean executeOnWarnings) {
		cond.onWarnings = executeOnWarnings;
		return this;
	}

	public TriggerConditionsBuilder buildErrors(boolean executeOnBuildErrors) {
		cond.onBuildErrors = executeOnBuildErrors;
		return this;
	}

	public TriggerConditionsBuilder internalErrors(boolean executeOnInternalErrors) {
		cond.onInternalErrors = executeOnInternalErrors;
		return this;
	}

	public TriggerConditionsBuilder analyzerWarnings(boolean executeOnAnalyzerWarnings) {
		cond.onAnalyzerWarnings = executeOnAnalyzerWarnings;
		return this;
	}

	public TriggerConditionsBuilder failingTests(boolean executeOnFailingTests) {
		cond.onFailingTests = executeOnFailingTests;
		return this;
	}

	public TriggerConditionsBuilder success(boolean executeOnSuccess) {
		cond.onSuccess = executeOnSuccess;
		return this;
	}
}
