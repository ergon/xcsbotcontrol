package ch.ergon.xcsbotcontrol.api.methods;

import ch.ergon.xcsbotcontrol.api.model.BotConfiguration;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class GetBotContainer {
	@SerializedName("_id")
	private String id;
	@SerializedName("_rev")
	private String rev;
	private String tinyID;
	private Map<String, String> group;
	private boolean requiresUpgrade;
	private String name;
	private int type;
	@SerializedName("integration_counter")
	private long integrationCounter;
	private String sourceControlBlueprintIdentifier;
	@SerializedName("doc_type")
	private String docType;
	private BotConfiguration configuration;
	private boolean simulator;
	private String osVersion;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRev() {
		return rev;
	}

	public void setRev(String rev) {
		this.rev = rev;
	}

	public String getTinyID() {
		return tinyID;
	}

	public void setTinyID(String tinyID) {
		this.tinyID = tinyID;
	}

	public Map<String, String> getGroup() {
		return group;
	}

	public void setGroup(Map<String, String> group) {
		this.group = group;
	}

	public boolean isRequiresUpgrade() {
		return requiresUpgrade;
	}

	public void setRequiresUpgrade(boolean requiresUpgrade) {
		this.requiresUpgrade = requiresUpgrade;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getIntegrationCounter() {
		return integrationCounter;
	}

	public void setIntegrationCounter(long integrationCounter) {
		this.integrationCounter = integrationCounter;
	}

	public String getSourceControlBlueprintIdentifier() {
		return sourceControlBlueprintIdentifier;
	}

	public void setSourceControlBlueprintIdentifier(String sourceControlBlueprintIdentifier) {
		this.sourceControlBlueprintIdentifier = sourceControlBlueprintIdentifier;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public BotConfiguration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(BotConfiguration configuration) {
		this.configuration = configuration;
	}

	public boolean isSimulator() {
		return simulator;
	}

	public void setSimulator(boolean simulator) {
		this.simulator = simulator;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	@Override
	public String toString() {
		return "Bot{" +
				"id='" + id + '\'' +
				", rev='" + rev + '\'' +
				", tinyID='" + tinyID + '\'' +
				", group=" + group +
				", requiresUpgrade=" + requiresUpgrade +
				", name='" + name + '\'' +
				", type=" + type +
				", integrationCounter=" + integrationCounter +
				", sourceControlBlueprintIdentifier='" + sourceControlBlueprintIdentifier + '\'' +
				", docType='" + docType + '\'' +
				", configuration=" + configuration +
				", simulator=" + simulator +
				", osVersion='" + osVersion + '\'' +
				'}';
	}
}
