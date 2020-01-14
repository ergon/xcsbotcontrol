package ch.ergon.xcsbotcontrol.api.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class EmailConfiguration {
	protected boolean includeCommitMessages = true;
	protected boolean includeLogs = false;
	protected boolean includeIssueDetails = true;
	protected boolean includeBotConfiguration = false;

	protected boolean emailCommitters = false;
	// List of repos (UUID) flagged true means commiters of repo will get mail if emailCommitters is true
	protected Map<String, Boolean> scmOptions = new HashMap<>();

	protected String fromAddress;
	protected String replyToAddress;
	protected List<String> ccAddresses = new ArrayList<>();
	protected List<String> additionalRecipients = new ArrayList<>();
	protected List<String> allowedDomainNames = new ArrayList<>();

	protected EmailScheduleType type = EmailScheduleType.AFTER_EACH_INTEGRATION;
	protected WeekDay weeklyScheduleDay;
	protected Integer minutesAfterHour;
	protected Integer hour;

	@Override
	public String toString() {
		return "EmailConfiguration{" +
				"includeCommitMessages=" + includeCommitMessages +
				", includeLogs=" + includeLogs +
				", includeIssueDetails=" + includeIssueDetails +
				", includeBotConfiguration=" + includeBotConfiguration +
				", emailCommitters=" + emailCommitters +
				", scmOptions=" + scmOptions +
				", fromAddress='" + fromAddress + '\'' +
				", replyToAddress='" + replyToAddress + '\'' +
				", ccAddresses=" + ccAddresses +
				", additionalRecipients=" + additionalRecipients +
				", allowedDomainNames=" + allowedDomainNames +
				", type=" + type +
				", weeklyScheduleDay=" + weeklyScheduleDay +
				", minutesAfterHour=" + minutesAfterHour +
				", hour=" + hour +
				'}';
	}
}
