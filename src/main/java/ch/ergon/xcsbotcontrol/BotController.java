package ch.ergon.xcsbotcontrol;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import ch.ergon.xcsbotcontrol.api.XcodeApi;
import ch.ergon.xcsbotcontrol.api.XcodeCredentials;
import ch.ergon.xcsbotcontrol.api.XcodeRequests;
import ch.ergon.xcsbotcontrol.api.methods.GetBotContainer;
import ch.ergon.xcsbotcontrol.api.model.Device;
import ch.ergon.xcsbotcontrol.api.model.*;
import ch.ergon.xcsbotcontrol.config.*;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import picocli.CommandLine;

@CommandLine.Command(name = "xcsbotcontrol", mixinStandardHelpOptions = true, version = "xcsbotcontrol 1.0.2",
		description = "Interact with the Xcode Server API.")
public class BotController implements Callable<Integer> {
	@VisibleForTesting
	protected XcodeApi xcodeApi = new XcodeRequests();

	@VisibleForTesting
	protected Keychain keychain = new Keychain();

	@CommandLine.Option(names = {"-a", "--action"}, required = true, description = "Action to execute [${COMPLETION-CANDIDATES}]")
	Action action;

	@CommandLine.Option(names = {"-s", "--server"}, description = "Xcode server host")
	String host;

	@CommandLine.Option(names = {"-n", "--bot-name"}, description = "Bot name")
	String botName;

	@CommandLine.Option(names = {"-b", "--branch"}, description = "Branch name")
	String branch;

	@CommandLine.Parameters( paramLabel = "CONFIG_FILES", description = "space delimited list of config files")
	List<String> configFiles = new ArrayList<>();

	@CommandLine.ArgGroup(exclusive = false)
	private Credentials credentials = new Credentials();

	static class Credentials {
		@CommandLine.Option(names = {"-u", "--user"}, description = "User name")
		String user;

		@CommandLine.Option(names = {"-p", "--password"}, arity = "0..1", description = "Password", interactive = true)
		char[] password;
	}

	public static void main(String[] args) {
		int exitCode = new CommandLine(new BotController())
				.setCaseInsensitiveEnumValuesAllowed(true)
				.execute(args);
		System.exit(exitCode);
	}

	enum Action {
		CREATE, DELETE, INTEGRATE, LIST, EXAMPLE
	}

	@Override
	public Integer call() {
		try {
			switch (action) {
				case CREATE:
					if (configFiles.isEmpty()) {
						System.out.println("Missing config file");
						return 1;
					}
					if (configFiles.size() > 1 && botName != null) {
						System.out.println("Cannot create multiple bots with same name");
						return 1;
					}

					for (String config : configFiles) {
						BotCreationSite site = createSite(config);
						if (host != null) {
							site.setXcodeServerHost(host);
						}
						if (botName != null) {
							site.setBotName(botName);
						}
						if (branch != null) {
							site.getMainRepository().setBranch(branch);
						}
						System.out.println("Creating bot: " + site.getBotName());
						createBotFromConfigFile(site);
					}
					break;
				case DELETE:
					if (botName != null && host != null) {
						System.out.println("Deleting bot: " + botName);
						xcodeApi.deleteBotByName(findCredentials(host), host, botName);
					} else if (!configFiles.isEmpty()) {
						for (String config : configFiles) {
							BotCreationSite site = createSite(config);
							System.out.println("Deleting bot: " + site.getBotName());
							xcodeApi.deleteBotByName(site.getCredentials(), site.getXcodeServerHost(), site.getBotName());
						}
					} else {
						System.out.println("Either config file or botName and host is required");
						return 1;
					}
					break;
				case INTEGRATE:
					if (botName != null && host != null) {
						System.out.println("Starting integration for bot: " + botName);
						xcodeApi.startIntegrationByName(findCredentials(host), host, botName);
					} else if (!configFiles.isEmpty()) {
						for (String config : configFiles) {
							BotCreationSite site = createSite(config);
							System.out.println("Starting integration for bot: " + site.getBotName());
							xcodeApi.startIntegrationByName(site.getCredentials(), site.getXcodeServerHost(), site.getBotName());
						}
					} else {
						System.out.println("Either config file or botName and host is required");
						return 1;
					}
					break;
				case LIST:
					if (host == null) {
						System.out.println("Missing host");
						return 1;
					}
					fetchBotConfigs(host);
					break;
				case EXAMPLE:
					System.out.println("Writing example config");
					writeExampleJson(exampleConfig());
					break;
				default:
					CommandLine.usage(this, System.out);
			}
		} catch (XcodeRequestException e) {
			System.out.println(e.getMessage());
			return 1;
		} catch (IOException e) {
			e.printStackTrace();
			return 1;
		} finally {
			if (credentials != null && credentials.password != null) {
				Arrays.fill(credentials.password, ' '); // ensure password is no longer in memory
			}
		}

		return 0;
	}

	private BotCreationSite createSite(String config) throws IOException {
		BotCreationSite site = createBasicSiteFromFile(config);
		site.setCredentials(findCredentials(site));
		return site;
	}

	private XcodeCredentials findCredentials(BotCreationSite site) throws IOException {
		if (Strings.isNullOrEmpty(site.getXcodeServerPassword())) {
			String[] host = site.getXcodeServerHost().split("\\.");
			return findCredentials(host[0]);
		} else {
			return site.getCredentials();
		}
	}

	private XcodeCredentials findCredentials(String host) throws IOException {
		if (credentials != null && credentials.user != null && credentials.password != null) {
			return new XcodeCredentials(credentials.user, String.valueOf(credentials.password));
		} else {
			return keychain.findItem(host);
		}
	}

	private void fetchBotConfigs(String host) {
		List<GetBotContainer> botContainers = xcodeApi.fetchBots(host);
		botContainers.forEach(System.out::println);
	}

	private void createBotFromConfigFile(BotCreationSite site) {
		try {
			List<Device> availableDevices = xcodeApi.fetchDevices(site.getCredentials(), site.getXcodeServerHost());
			site.fillDevices(availableDevices);
			Map<SimulatorType, DevicePlatform> platformsBySimulatorType = xcodeApi.fetchPlatforms(site.getCredentials(), site.getXcodeServerHost()).stream()
					.filter(p -> p.getSimulatorIdentifier() != null)
					.collect(Collectors.toMap(p -> p.getSimulatorIdentifier(), p -> p));
			site.setPlatforms(platformsBySimulatorType);
			if (site.getOverrideToolchainName() != null) {
				List<Toolchain> availableToolchains = xcodeApi.fetchToolchains(site.getCredentials(), site.getXcodeServerHost());
				site.fillToolchains(availableToolchains);
			}
		} catch (XcodeRequestException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

//		String requestBody = site.createBotRequestBody();
//		System.out.println(requestBody);
		try {
			xcodeApi.createBot(site);
		} catch (XcodeRequestException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	private BotCreationSite createBasicSiteFromFile(String filepath) throws IOException {
		BotCreationSite site = BotCreationSite.fromJson(filepath);
		fillRepoPrivateKeys(site);
		return site;
	}

	private void fillRepoPrivateKeys(BotCreationSite site) {
		String mainRepoPrivateKey = findRepoSshPrivateKey(site.getMainRepository());
		site.getMainRepository().setSshPrivateKey(mainRepoPrivateKey);

		for (Repository repo : site.getSecondaryRepositories()) {
			String privateKey = findRepoSshPrivateKey(repo);
			repo.setSshPrivateKey(privateKey);
		}
	}

	private String findRepoSshPrivateKey(Repository repo) {
		XcodeCredentials mainRepoSshPrivateKey = null;
		if (!Strings.isNullOrEmpty(repo.getSshPrivateKeyKeyChainItem())) {
			try {
				mainRepoSshPrivateKey = keychain.findItem(repo.getSshPrivateKeyKeyChainItem());
			} catch (IOException e) {
				// Ignore and use the ones provided in config file
			}

			if (mainRepoSshPrivateKey != null && !Strings.isNullOrEmpty(mainRepoSshPrivateKey.getPassword())) {
				String key = mainRepoSshPrivateKey.getPassword().replaceAll("^.*\\s{2}", "");
				key = key.replaceAll("\\\\012", "\n");
				return key;
			}
		}
		return repo.getSshPrivateKey();
	}

	private static void writeExampleJson(BotCreationSite site) {
		Gson gson=new GsonBuilder().setPrettyPrinting().create();
		String json =gson.toJson(site);
		try (PrintWriter writer = new PrintWriter("example.json");){
			writer.print(json);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static BotCreationSite exampleConfig() {
		BotCreationSite site = new BotCreationSite();
		site.setXcodeServerHost("host.example.ch");

		site.setBotName("scriptedBot");
		site.setBuildScheme("SomeScheme");
		site.setBuildConfiguration("Debug");
		site.setClean(Clean.ONCE_A_DAY);
		site.setPerformAnalyze(true);
		site.setPerformArchive(false);
		site.setPerformArchiveExport(false);
		site.setPerformTest(true);

		site.setPreBuildScript("#!/bin/bash\n"
				+ "cd $XCS_PRIMARY_REPO_DIR\n"
				+ "TRIGGER_SCRIPT=ContinuousIntegration/Scripts/preBuildScript.sh\n"
				+ "if command -V $TRIGGER_SCRIPT; then $TRIGGER_SCRIPT; fi");

		PostBuildScriptTrigger postBuildScriptTrigger = new PostBuildScriptTrigger();
		postBuildScriptTrigger.setScript("#!/bin/bash\n"
				+ "cd $XCS_PRIMARY_REPO_DIR\n"
				+ "TRIGGER_SCRIPT=ContinuousIntegration/Scripts/postBuildScriptTrigger.sh\n"
				+ "if command -V $TRIGGER_SCRIPT; then $TRIGGER_SCRIPT; fi");
		postBuildScriptTrigger.setConditions(Sets.newHashSet(PostBuildTriggerCondition.values()));
		site.getPostBuildScriptTriggers().add(postBuildScriptTrigger);

		PostBuildEmailTrigger postBuildEmailTrigger = new PostBuildEmailTrigger();
		postBuildEmailTrigger.setIncludeCommitMessages(true);
		postBuildEmailTrigger.setIncludeIssueDetails(true);
		postBuildEmailTrigger.setIncludeBotConfiguration(false);
		postBuildEmailTrigger.setIncludeLogs(false);
		postBuildEmailTrigger.setFromAddress("noreply@example.ch");
		postBuildEmailTrigger.getRecipientAdresses().add("developer@example.ch");
		postBuildEmailTrigger.setAllowedDomainName("example.ch");
		postBuildEmailTrigger.setSchedule(new EmailSchedule());
		postBuildEmailTrigger.setConditions(Sets.newHashSet(PostBuildTriggerCondition.ANALYZER_WARNINGS,
				PostBuildTriggerCondition.BUILD_ERRORS, PostBuildTriggerCondition.FAILING_TESTS,
				PostBuildTriggerCondition.INTERNAL_ERRORS, PostBuildTriggerCondition.WARNINGS));
		site.getPostBuildEmailTriggers().add(postBuildEmailTrigger);

		site.getDeviceNames().add("iPhone 8");

		site.setMainRepositoryProjectFilePath("ProjectName/ProjectName.xcworkspace");

		Repository main = new Repository();
		main.setBranch("master");
		main.setWorkingCopyPath("working_path/");
		main.setUrl("ssh://bitbucket.example.ch:7999/repopath/projectname.git");
		main.setSshFingerprint("ABC123");
		main.setSshPrivateKey("-----BEGIN RSA PRIVATE KEY-----\n-----END RSA PRIVATE KEY-----");
		main.setSshPublicKey("ssh-rsa ABCD123= xcodeserver@example.ch");
		site.setMainRepository(main);

		Repository subRepo = new Repository();
		subRepo.setBranch("master");
		subRepo.setWorkingCopyPath("working_path/dependencies/dependencyname/");
		subRepo.setUrl("ssh://bitbucket.example.ch:7999/repopath/dependencyname.git");
		subRepo.setSshFingerprint("ABC123");
		subRepo.setSshPrivateKey("-----BEGIN RSA PRIVATE KEY-----\n-----END RSA PRIVATE KEY-----");
		subRepo.setSshPublicKey("ssh-rsa ABCD123= xcodeserver@example.ch");
		site.getSecondaryRepositories().add(subRepo);

		return site;
	}
}
