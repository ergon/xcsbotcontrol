package ch.ergon.xcsbotcontrol.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import ch.ergon.xcsbotcontrol.api.XcodeCredentials;
import ch.ergon.xcsbotcontrol.api.model.Device;
import ch.ergon.xcsbotcontrol.api.model.*;
import ch.ergon.xcsbotcontrol.api.methods.CreateBotRequest;
import ch.ergon.xcsbotcontrol.gson.GsonFactory;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.gson.stream.JsonReader;

public class BotCreationSite {
	private String xcodeServerHost;
	private String xcodeServerUsername;
	private String xcodeServerPassword;

	private String botName;
	private String buildScheme;
	private String buildConfiguration;
	private String overrideToolchainName;
	private Toolchain overrideToolchain;
	private boolean manageCertsAndProfiles;
	private boolean addMissingDevicesToTeam;
	private Clean clean;
	private boolean performAnalyze;
	private boolean performArchive;
	private boolean performArchiveExport;
	private boolean performTest;

	private Schedule schedule;

	private String preBuildScript;
	private List<PostBuildScriptTrigger> postBuildScriptTriggers = new ArrayList<>();
	private List<PostBuildEmailTrigger> postBuildEmailTriggers = new ArrayList<>();

	private List<Device> devices = new ArrayList<>();
	private Set<String> deviceNames = new HashSet<>();
	private String platformId;
	private Map<SimulatorType, DevicePlatform> platforms;

	private String mainRepositoryProjectFilePath;
	private Repository mainRepository;

	private List<Repository> secondaryRepositories = new ArrayList<>();

	public static BotCreationSite fromJson(String filename) throws FileNotFoundException {
		JsonReader reader = new JsonReader(new java.io.FileReader(new File(filename)));
		return GsonFactory.defaultGson().fromJson(reader, BotCreationSite.class);
	}

	public XcodeCredentials getCredentials() {
		return new XcodeCredentials(xcodeServerUsername, xcodeServerPassword);
	}

	public void setCredentials(XcodeCredentials credentials) {
		xcodeServerUsername = credentials.getUsername();
		xcodeServerPassword = credentials.getPassword();
	}

	public String createBotRequestBody() {
		SourceControlBlueprintBuilder blueprintBuilder = SourceControlBlueprintBuilder.blueprint()
				.name(botName)
				.mainRepositoryRelativePathToProject(mainRepositoryProjectFilePath) // path to .xcworkspace file
				.addRepository(SourceControlBlueprintBuilder.RepositoryBuilder.repository()
						.branch(mainRepository.getBranch())
						.certificateFingerprint(mainRepository.getSshFingerprint())
						.primary()
						.remoteUrl(mainRepository.getUrl())
						.workingCopyPath(mainRepository.getWorkingCopyPath()) // checkout path
						.withAuthentication(SourceControlBlueprintBuilder.AuthenticationBuilder.authentication()
								.sshPublicKey(mainRepository.getSshPublicKey())
								.sshPrivateKey(mainRepository.getSshPrivateKey())
						));

		for (Repository secondaryRepo : secondaryRepositories) {
			blueprintBuilder.addRepository(SourceControlBlueprintBuilder.RepositoryBuilder.repository()
					.branch(secondaryRepo.getBranch())
					.certificateFingerprint(secondaryRepo.getSshFingerprint())
					.remoteUrl(secondaryRepo.getUrl())
					.workingCopyPath(secondaryRepo.getWorkingCopyPath())
					.withAuthentication(SourceControlBlueprintBuilder.AuthenticationBuilder.authentication()
							.sshPublicKey(secondaryRepo.getSshPublicKey())
							.sshPrivateKey(secondaryRepo.getSshPrivateKey())
					));
		}

		BotConfigurationBuilder configurationBuilder = BotConfigurationBuilder.botConfiguration()
				.schemeName(buildScheme)
				.buildConfiguration(buildConfiguration)
				.builtFromClean(clean)
				.withProvisioningConfiguration(ProvisioningConfigurationBuilder.provisioningConfig()
						.manageCertsAndProfiles(manageCertsAndProfiles)
						.addMissingDevicesToTeam(addMissingDevicesToTeam))
				.performAnalyze(performAnalyze)
				.performArchiveAndExport(performArchive, performArchiveExport)
				.performTests(performTest)
				.withScmBlueprint(blueprintBuilder);

		for (PostBuildScriptTrigger trigger : postBuildScriptTriggers) {
			configurationBuilder.addTrigger(TriggerBuilder.trigger()
					.name("postBuildScript")
					.phasePostbuild(TriggerConditionsBuilder.triggerConditions()
							.analyzerWarnings(trigger.getConditions().contains(PostBuildTriggerCondition.ANALYZER_WARNINGS))
							.buildErrors(trigger.getConditions().contains(PostBuildTriggerCondition.BUILD_ERRORS))
							.failingTests(trigger.getConditions().contains(PostBuildTriggerCondition.FAILING_TESTS))
							.internalErrors(trigger.getConditions().contains(PostBuildTriggerCondition.INTERNAL_ERRORS))
							.success(trigger.getConditions().contains(PostBuildTriggerCondition.SUCCESS))
							.warnings(trigger.getConditions().contains(PostBuildTriggerCondition.WARNINGS)))
					.script(replaceWithUnixNewLines(trigger.getScript())));
		}

		for (PostBuildEmailTrigger trigger : postBuildEmailTriggers) {
			TriggerBuilder triggerBuilder = TriggerBuilder.trigger()
					.name("postBuildMail");

			// TODO move conditionsbuilder as it is only relevant in AFTER EACH INTEGRATION
			EmailConfigurationBuilder emailBuilder = EmailConfigurationBuilder.emailConfiguration()
					.includeCommitMessages(trigger.isIncludeCommitMessages())
					.includeLogs(trigger.isIncludeLogs())
					.includeIssueDetails(trigger.isIncludeIssueDetails())
					.includeBotConfiguration(trigger.isIncludeBotConfiguration())
					.fromAddress(trigger.getFromAddress())
					.replyToAddress(trigger.getFromAddress())
					.addAllowedDomainName(trigger.getAllowedDomainName());

			for (String recipient : trigger.getRecipientAdresses()){
				emailBuilder.addAdditionalRecipient(recipient);
			}

			EmailSchedule schedule = trigger.getSchedule();
			switch (schedule.getType()) {
				case AFTER_EACH_INTEGRATION:
					emailBuilder.scheduleAfterIntegration();
					triggerBuilder.phasePostbuild(TriggerConditionsBuilder.triggerConditions()
							.analyzerWarnings(trigger.getConditions().contains(PostBuildTriggerCondition.ANALYZER_WARNINGS))
							.buildErrors(trigger.getConditions().contains(PostBuildTriggerCondition.BUILD_ERRORS))
							.failingTests(trigger.getConditions().contains(PostBuildTriggerCondition.FAILING_TESTS))
							.internalErrors(trigger.getConditions().contains(PostBuildTriggerCondition.INTERNAL_ERRORS))
							.success(trigger.getConditions().contains(PostBuildTriggerCondition.SUCCESS))
							.warnings(trigger.getConditions().contains(PostBuildTriggerCondition.WARNINGS)));
					break;
				case DAILY:
					emailBuilder.scheduleDaily(schedule.getHourOfDay(), schedule.getMinutesAfterHour());
					break;
				case WEEKLY:
					emailBuilder.scheduleWeekly(schedule.getDayOfWeek(), schedule.getHourOfDay(), schedule.getMinutesAfterHour());
					break;
			}

			configurationBuilder.addTrigger(triggerBuilder
					.email(emailBuilder));
		}

		if (!Strings.isNullOrEmpty(preBuildScript)) {
			configurationBuilder.addTrigger(TriggerBuilder.trigger()
					.name("preBuild")
					.phasePrebuild()
					.script(replaceWithUnixNewLines(preBuildScript)));
		}

		if (overrideToolchain != null) {
			configurationBuilder.overrideToolchain(overrideToolchain);
		}

		switch (schedule.getType()) {
			case MANUAL:
				configurationBuilder.scheduleManual();
				break;
			case ON_COMMIT:
				configurationBuilder.scheduleOnCommit();
				break;
			case HOURLY:
				configurationBuilder.scheduleHourly(schedule.getMinutesAfterHour());
				break;
			case DAILY:
				configurationBuilder.scheduleDaily(schedule.getHourOfDay());
				break;
			case WEEKLY:
				configurationBuilder.scheduleWeekly(schedule.getDayOfWeek(), schedule.getHourOfDay());
				break;
		}

		if (!devices.isEmpty()) {
			DeviceSpecificationBuilder deviceSpecBuilder = DeviceSpecificationBuilder.deviceSpecifiation();
			for (Device device : devices) {
				deviceSpecBuilder.addFilter(DeviceFilterBuilder.filter()
						.architecture(DeviceArchitectureType.IOS_WATCH_TV_OS)
						.platform(platforms.get(SimulatorType.IPHONE)) // TODO: make dynamic
						.type(DeviceFilterType.SELECTED_DEVICES_AND_SIMULATORS));

				deviceSpecBuilder.addDeviceIdentifier(device.getId().trim());
			}
			configurationBuilder.withDeviceSpec(deviceSpecBuilder);
		}

		CreateBotRequest request = new CreateBotRequest(
				configurationBuilder.build(),
				ImmutableMap.of("name", UUID.randomUUID().toString()),
				false,
				botName
				);

		return GsonFactory.defaultGson().toJson(request);
	}

	private static String replaceWithUnixNewLines(String text) {
		String[] lines = text.split("\n");
		String fixed = "";
		for (String line : lines) {
			fixed += line.trim() + "\n";
		}
		return fixed;
	}

	public void fillDevices(List<Device> availableDevices) {
		if (!deviceNames.isEmpty()) {
			for (String deviceName : deviceNames) {
				Optional<Device> match = availableDevices.stream()
						.filter(d -> d.isConnected())
						.filter(d -> d.getName().contains(deviceName))
						.findFirst();
				if (match.isPresent()) {
					devices.add(match.get());
				}
			}
		}
	}

	public void fillToolchains(List<Toolchain> availableToolchains) {
		Optional<Toolchain> match = availableToolchains.stream()
				.filter(t -> t.getDisplayName().contains(overrideToolchainName))
				.findFirst();
		if (match.isPresent()) {
			overrideToolchain = match.get();
		}
	}

	public void setPlatforms(Map<SimulatorType, DevicePlatform> platforms) {
		this.platforms = platforms;
	}

	public DevicePlatform getPlatform(SimulatorType type) {
		return platforms.get(type);
	}

	public String getXcodeServerHost() {
		return xcodeServerHost;
	}

	public void setXcodeServerHost(String xcodeServerHost) {
		this.xcodeServerHost = xcodeServerHost;
	}

	public String getXcodeServerUsername() {
		return xcodeServerUsername;
	}

	public void setXcodeServerUsername(String xCodeServerUsername) {
		this.xcodeServerUsername = xCodeServerUsername;
	}

	public String getXcodeServerPassword() {
		return xcodeServerPassword;
	}

	public void setXcodeServerPassword(String xCoderServerPassword) {
		this.xcodeServerPassword = xCoderServerPassword;
	}

	public String getBotName() {
		return botName;
	}

	public void setBotName(String botName) {
		this.botName = botName;
	}

	public String getBuildScheme() {
		return buildScheme;
	}

	public void setBuildScheme(String buildScheme) {
		this.buildScheme = buildScheme;
	}

	public String getBuildConfiguration() {
		return buildConfiguration;
	}

	public void setBuildConfiguration(String buildConfiguration) {
		this.buildConfiguration = buildConfiguration;
	}

	public boolean isManageCertsAndProfiles() {
		return manageCertsAndProfiles;
	}

	public void setManageCertsAndProfiles(boolean manageCertsAndProfiles) {
		this.manageCertsAndProfiles = manageCertsAndProfiles;
	}

	public boolean isAddMissingDevicesToTeam() {
		return addMissingDevicesToTeam;
	}

	public void setAddMissingDevicesToTeam(boolean addMissingDevicesToTeam) {
		this.addMissingDevicesToTeam = addMissingDevicesToTeam;
	}

	public Clean getClean() {
		return clean;
	}

	public void setClean(Clean clean) {
		this.clean = clean;
	}

	public boolean isPerformAnalyze() {
		return performAnalyze;
	}

	public void setPerformAnalyze(boolean performAnalyze) {
		this.performAnalyze = performAnalyze;
	}

	public boolean isPerformArchive() {
		return performArchive;
	}

	public void setPerformArchive(boolean performArchive) {
		this.performArchive = performArchive;
	}

	public boolean isPerformArchiveExport() {
		return performArchiveExport;
	}

	public void setPerformArchiveExport(boolean performArchiveExport) {
		this.performArchiveExport = performArchiveExport;
	}

	public boolean isPerformTest() {
		return performTest;
	}

	public void setPerformTest(boolean performTest) {
		this.performTest = performTest;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public String getPreBuildScript() {
		return preBuildScript;
	}

	public void setPreBuildScript(String preBuildScript) {
		this.preBuildScript = preBuildScript;
	}

	public List<PostBuildScriptTrigger> getPostBuildScriptTriggers() {
		return postBuildScriptTriggers;
	}

	public void setPostBuildScriptTriggers(List<PostBuildScriptTrigger> postBuildScriptTriggers) {
		this.postBuildScriptTriggers = postBuildScriptTriggers;
	}

	public List<PostBuildEmailTrigger> getPostBuildEmailTriggers() {
		return postBuildEmailTriggers;
	}

	public void setPostBuildEmailTriggers(List<PostBuildEmailTrigger> postBuildEmailTriggers) {
		this.postBuildEmailTriggers = postBuildEmailTriggers;
	}

	public List<Device> getDevices() {
		return devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

	public Set<String> getDeviceNames() {
		return deviceNames;
	}

	public void setDeviceNames(Set<String> deviceNames) {
		this.deviceNames = deviceNames;
	}

	public String getMainRepositoryProjectFilePath() {
		return mainRepositoryProjectFilePath;
	}

	public void setMainRepositoryProjectFilePath(String mainRepositoryProjectFilePath) {
		this.mainRepositoryProjectFilePath = mainRepositoryProjectFilePath;
	}

	public Repository getMainRepository() {
		return mainRepository;
	}

	public void setMainRepository(Repository mainRepository) {
		this.mainRepository = mainRepository;
	}

	public List<Repository> getSecondaryRepositories() {
		return secondaryRepositories;
	}

	public void setSecondaryRepositories(List<Repository> secondaryRepositories) {
		this.secondaryRepositories = secondaryRepositories;
	}

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	public String getOverrideToolchainName() {
		return overrideToolchainName;
	}

	public void setOverrideToolchainName(String overrideToolchainName) {
		this.overrideToolchainName = overrideToolchainName;
	}
}

