package ch.ergon.xcsbotcontrol.config;

import java.util.HashSet;
import java.util.Set;

public class PostBuildScriptTrigger {
	private Set<PostBuildTriggerCondition> conditions = new HashSet<>();
	private String script;

	public Set<PostBuildTriggerCondition> getConditions() {
		return conditions;
	}

	public void setConditions(Set<PostBuildTriggerCondition> conditions) {
		this.conditions = conditions;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}
}
