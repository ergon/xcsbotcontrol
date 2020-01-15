package ch.ergon.xcsbotcontrol.api.model;

import java.util.ArrayList;
import java.util.List;

public class BotConfiguration {

	/*
	 * Scheduling
	 */

	protected ScheduleType scheduleType = ScheduleType.ON_COMMIT;
	// Only relevant if ScheduleType is set to PERIODICALLY
	protected ScheduleInterval periodicScheduleInterval = ScheduleInterval.DAILY;

	/* Minutes after the hour to integrate
		Value: 0 to 59
		 Dependencies:
			 - 'periodicScheduleInterval' set to 1 (Hourly)
	*/
	protected int minutesAfterHourToIntegrate;

	/* Hour of integration
		Value: 0 (midnight) to 23
		Dependencies:
			- 'periodicScheduleInterval' set to 2 (Daily) or 3 (Weekly)
	*/
	protected int hourOfIntegration;

	/* Weekly schedule day
		1: Monday
		2: Tuesday
		3: Wednesday
		4: Thursday
		5: Friday
		6: Saturday
		7: Sunday
		Dependencies:
			- 'periodicScheduleInterval' set to 3 (Weekly) */
	protected WeekDay weeklyScheduleDay;


	/*
	 * Scheme
	 */

	protected String schemeName = "DefaultSchemeName";

	protected String buildConfiguration =  "Release";

	protected Clean builtFromClean = Clean.ALWAYS;


	/*
	 * Actions
	 */

	protected boolean performsAnalyzeAction = true;

	protected boolean performsTestAction  = true;

	protected boolean performsArchiveAction = false;

	protected boolean exportsProductFromArchive = false;


	/*
	 * Testing
	 */

	protected CodeCoverage codeCoveragePreference = CodeCoverage.USE_SCHEME_SETTING;

	/*
	 Legacy property of what devices should be tested on. Now moved to `DeviceSpecification`, but
	 sending 0 or 7 still required. Sigh.
	 */
	protected final TestingDestination testingDestinationType = TestingDestination.IOS_AND_WATCH;


	/*
	 * Provisioning
	 */

	protected ProvisioningConfiguration provisioningConfiguration;


	/*
	 * Misc
	 */

	protected boolean disableAppThinning = false;

	protected boolean useParallelDeviceTesting = true;

	protected Toolchain overrideToolchain;


	/*
	 * Triggers
	 */

	protected List<Trigger> triggers = new ArrayList<>();


	/*
	 * Devices
	 */

	protected List<String> testingDeviceIDs = new ArrayList<>();

	/* Device specification
		Value: array of <device ID>
	    Dependencies:
			 - 'performsTestAction' set to true
	 */
	protected DeviceSpecification deviceSpecification;

	protected SourceControlBlueprint sourceControlBlueprint;

	public SourceControlBlueprint getSourceControlBlueprint() {
		return sourceControlBlueprint;
	}

	@Override
	public String toString() {
		return "BotConfiguration{" +
				"scheduleType=" + scheduleType +
				", periodicScheduleInterval=" + periodicScheduleInterval +
				", minutesAfterHourToIntegrate=" + minutesAfterHourToIntegrate +
				", hourOfIntegration=" + hourOfIntegration +
				", weeklyScheduleDay=" + weeklyScheduleDay +
				", schemeName='" + schemeName + '\'' +
				", buildConfiguration='" + buildConfiguration + '\'' +
				", builtFromClean=" + builtFromClean +
				", performsAnalyzeAction=" + performsAnalyzeAction +
				", performsTestAction=" + performsTestAction +
				", performsArchiveAction=" + performsArchiveAction +
				", exportsProductFromArchive=" + exportsProductFromArchive +
				", codeCoveragePreference=" + codeCoveragePreference +
				", testingDestinationType=" + testingDestinationType +
				", provisioningConfiguration=" + provisioningConfiguration +
				", disableAppThinning=" + disableAppThinning +
				", useParallelDeviceTesting=" + useParallelDeviceTesting +
				", overrideToolchain=" + overrideToolchain +
				", triggers=" + triggers +
				", testingDeviceIDs=" + testingDeviceIDs +
				", deviceSpecification=" + deviceSpecification +
				", sourceControlBlueprint=" + sourceControlBlueprint +
				'}';
	}
}

