package ch.ergon.xcsbotcontrol.api;

import ch.ergon.xcsbotcontrol.api.methods.GetBotContainer;
import ch.ergon.xcsbotcontrol.api.model.Device;
import ch.ergon.xcsbotcontrol.api.model.DevicePlatform;
import ch.ergon.xcsbotcontrol.api.model.Toolchain;
import ch.ergon.xcsbotcontrol.config.BotCreationSite;

import java.util.List;
import java.util.Optional;

public interface XcodeApi {
	void createBot(BotCreationSite site);

	List<Device> fetchDevices(XcodeCredentials credentials, String host);

	List<DevicePlatform> fetchPlatforms(XcodeCredentials credentials, String host);

	List<Toolchain> fetchToolchains(XcodeCredentials credentials, String host);

	void startIntegrationByName(XcodeCredentials credentials, String host, String botName);

	void startIntegration(XcodeCredentials credentials, String host, String botId);

	void deleteBotByName(XcodeCredentials credentials, String host, String botName);

	void deleteBot(XcodeCredentials credentials, String host, String botId);

	Optional<String> findBotIdByName(String host, String botName);

	List<GetBotContainer> fetchBots(String host);
}
