package ch.ergon.xcsbotcontrol.api.model;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Strings;

public class BotConfigurationBuilder {
	private final BotConfiguration config = new BotConfiguration();
	private List<TriggerBuilder> triggerBuilders = new ArrayList<>();
	private DeviceSpecificationBuilder deviceSpecBuilder = DeviceSpecificationBuilder.deviceSpecifiation();
	private ProvisioningConfigurationBuilder provisioningConfigBuilder = ProvisioningConfigurationBuilder.provisioningConfig();
	private SourceControlBlueprintBuilder blueprintBuilder;

	public static BotConfigurationBuilder botConfiguration() {
		return new BotConfigurationBuilder();
	}

	public BotConfigurationBuilder scheduleManual() {
		config.scheduleType = ScheduleType.MANUAL;
		config.periodicScheduleInterval = ScheduleInterval.NONE;
		return this;
	}

	public BotConfigurationBuilder scheduleOnCommit() {
		config.scheduleType = ScheduleType.ON_COMMIT;
		config.periodicScheduleInterval = ScheduleInterval.NONE;
		return this;
	}

	public BotConfigurationBuilder scheduleHourly(int minutesAfterHour) {
		config.scheduleType = ScheduleType.PERIODICALLY;
		config.periodicScheduleInterval = ScheduleInterval.HOURLY;
		config.minutesAfterHourToIntegrate = minutesAfterHour;
		return this;
	}

	public BotConfigurationBuilder scheduleDaily(int hourOfDay) {
		config.scheduleType = ScheduleType.PERIODICALLY;
		config.periodicScheduleInterval = ScheduleInterval.DAILY;
		config.hourOfIntegration = hourOfDay;
		return this;
	}

	public BotConfigurationBuilder scheduleWeekly(WeekDay dayOfWeek, int hourOfDay) {
		config.scheduleType = ScheduleType.PERIODICALLY;
		config.periodicScheduleInterval = ScheduleInterval.WEEKLY;
		config.weeklyScheduleDay = dayOfWeek;
		config.hourOfIntegration = hourOfDay;
		return this;
	}

	public BotConfigurationBuilder schemeName(String name) {
		config.schemeName = name;
		return this;
	}

	public BotConfigurationBuilder buildConfiguration(String name) {
		if (Strings.isNullOrEmpty(name)) {
			config.buildConfiguration = null;
		} else {
			config.buildConfiguration = name;
		}
		return this;
	}

	public BotConfigurationBuilder builtFromClean(Clean clean) {
		config.builtFromClean = clean;
		return this;
	}

	public BotConfigurationBuilder overrideToolchain(Toolchain toolchain) {
		config.overrideToolchain = toolchain;
		return this;
	}

	public BotConfigurationBuilder performAnalyze(boolean analyze) {
		config.performsAnalyzeAction = analyze;
		return this;
	}

	public BotConfigurationBuilder performTests(boolean test) {
		config.performsTestAction = test;
		return this;
	}

	public BotConfigurationBuilder performArchiveAndExport(boolean archive, boolean export) {
		config.performsArchiveAction = archive;
		if (archive && export) {
			config.exportsProductFromArchive = true;
		}
		return this;
	}

	public BotConfigurationBuilder codeCoverage(CodeCoverage coverage) {
		config.codeCoveragePreference = coverage;
		return this;
	}

	public BotConfigurationBuilder addTrigger(TriggerBuilder trigger) {
		this.triggerBuilders.add(trigger);
		return this;
	}

	public BotConfigurationBuilder withDeviceSpec(DeviceSpecificationBuilder spec) {
		deviceSpecBuilder = spec;
		return this;
	}

	public BotConfigurationBuilder withScmBlueprint(SourceControlBlueprintBuilder blueprint) {
		blueprintBuilder = blueprint;
		return this;
	}

	public BotConfigurationBuilder withProvisioningConfiguration(ProvisioningConfigurationBuilder provisioning) {
		provisioningConfigBuilder = provisioning;
		return this;
	}

	public BotConfiguration build() {
		for (TriggerBuilder triggerBuilder : triggerBuilders) {
			config.triggers.add(triggerBuilder.build());
		}
		if (blueprintBuilder != null) {
			config.sourceControlBlueprint = blueprintBuilder.build();
		}
		config.deviceSpecification = deviceSpecBuilder.build();
		config.provisioningConfiguration = provisioningConfigBuilder.build();
		return config;
	}

}
