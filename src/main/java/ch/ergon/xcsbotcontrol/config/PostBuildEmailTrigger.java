package ch.ergon.xcsbotcontrol.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PostBuildEmailTrigger {
	private boolean includeCommitMessages;
	private boolean includeLogs;
	private boolean includeIssueDetails;
	private boolean includeBotConfiguration;
	private String fromAddress;
	private String replyToAddress;
	private List<String> recipientAdresses = new ArrayList<>();
	private String allowedDomainName;
	private EmailSchedule schedule = new EmailSchedule();
	private Set<PostBuildTriggerCondition> conditions = new HashSet<>(); // only relevant for schedule after integration

	public boolean isIncludeCommitMessages() {
		return includeCommitMessages;
	}

	public void setIncludeCommitMessages(boolean includeCommitMessages) {
		this.includeCommitMessages = includeCommitMessages;
	}

	public boolean isIncludeLogs() {
		return includeLogs;
	}

	public void setIncludeLogs(boolean includeLogs) {
		this.includeLogs = includeLogs;
	}

	public boolean isIncludeIssueDetails() {
		return includeIssueDetails;
	}

	public void setIncludeIssueDetails(boolean includeIssueDetails) {
		this.includeIssueDetails = includeIssueDetails;
	}

	public boolean isIncludeBotConfiguration() {
		return includeBotConfiguration;
	}

	public void setIncludeBotConfiguration(boolean includeBotConfiguration) {
		this.includeBotConfiguration = includeBotConfiguration;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getReplyToAddress() {
		return replyToAddress;
	}

	public void setReplyToAddress(String replyToAddress) {
		this.replyToAddress = replyToAddress;
	}

	public List<String> getRecipientAdresses() {
		return recipientAdresses;
	}

	public void setRecipientAdresses(List<String> recipientAdresses) {
		this.recipientAdresses = recipientAdresses;
	}

	public String getAllowedDomainName() {
		return allowedDomainName;
	}

	public void setAllowedDomainName(String allowedDomainName) {
		this.allowedDomainName = allowedDomainName;
	}

	public EmailSchedule getSchedule() {
		return schedule;
	}

	public void setSchedule(EmailSchedule schedule) {
		this.schedule = schedule;
	}

	public Set<PostBuildTriggerCondition> getConditions() {
		return conditions;
	}

	public void setConditions(Set<PostBuildTriggerCondition> conditions) {
		this.conditions = conditions;
	}
}
