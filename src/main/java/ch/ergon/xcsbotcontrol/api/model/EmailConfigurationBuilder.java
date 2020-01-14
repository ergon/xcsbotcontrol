package ch.ergon.xcsbotcontrol.api.model;

public class EmailConfigurationBuilder {
	private final EmailConfiguration config = new EmailConfiguration();

	public static EmailConfigurationBuilder emailConfiguration() {
		return new EmailConfigurationBuilder();
	}

	public EmailConfiguration build() {
		return config;
	}

	public EmailConfigurationBuilder includeCommitMessages(boolean include) {
		config.includeCommitMessages = include;
		return this;
	}

	public EmailConfigurationBuilder includeLogs(boolean include) {
		config.includeLogs = include;
		return this;
	}

	public EmailConfigurationBuilder includeIssueDetails(boolean include) {
		config.includeIssueDetails = include;
		return this;
	}

	public EmailConfigurationBuilder includeBotConfiguration(boolean include) {
		config.includeBotConfiguration = include;
		return this;
	}

	public EmailConfigurationBuilder emailCommitters(String repositoryUUID, boolean notify) {
		config.emailCommitters = true;
		config.scmOptions.put(repositoryUUID, notify);
		return this;
	}

	public EmailConfigurationBuilder fromAddress(String address) {
		config.fromAddress = address;
		return this;
	}

	public EmailConfigurationBuilder replyToAddress(String address) {
		config.replyToAddress = address;
		return this;
	}

	public EmailConfigurationBuilder addCcAddress(String emailAddress) {
		config.ccAddresses.add(emailAddress);
		return this;
	}

	public EmailConfigurationBuilder addAdditionalRecipient(String emailAddress) {
		config.additionalRecipients.add(emailAddress);
		return this;
	}

	public EmailConfigurationBuilder addAllowedDomainName(String domainName) {
		config.allowedDomainNames.add(domainName);
		return this;
	}

	public EmailConfigurationBuilder scheduleAfterIntegration() {
		config.type = EmailScheduleType.AFTER_EACH_INTEGRATION;
		return this;
	}

	public EmailConfigurationBuilder scheduleDaily(int hourOfDay, int minutesAfterHour) {
		config.type = EmailScheduleType.DAILY;
		config.hour = hourOfDay;
		config.minutesAfterHour = minutesAfterHour;
		return this;
	}

	public EmailConfigurationBuilder scheduleWeekly(WeekDay dayOfWeek, int hourOfDay, int minutesAfterHour) {
		config.type = EmailScheduleType.WEEKLY;
		config.weeklyScheduleDay = dayOfWeek;
		config.hour = hourOfDay;
		config.minutesAfterHour = minutesAfterHour;
		return this;
	}
}
