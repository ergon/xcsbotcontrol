package ch.ergon.xcsbotcontrol.api.model;

final class TriggerConditions {
	protected transient int status = 2; // undocumented, skipping in json
	protected boolean onWarnings = true;
	protected boolean onBuildErrors = true;
	protected boolean onInternalErrors = true;
	protected boolean onAnalyzerWarnings = true;
	protected boolean onFailingTests = true;
	protected boolean onSuccess = true;

	@Override
	public String toString() {
		return "TriggerConditions{" +
				"status=" + status +
				", onWarnings=" + onWarnings +
				", onBuildErrors=" + onBuildErrors +
				", onInternalErrors=" + onInternalErrors +
				", onAnalyzerWarnings=" + onAnalyzerWarnings +
				", onFailingTests=" + onFailingTests +
				", onSuccess=" + onSuccess +
				'}';
	}
}
