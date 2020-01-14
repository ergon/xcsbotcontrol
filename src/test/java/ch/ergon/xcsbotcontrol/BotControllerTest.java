package ch.ergon.xcsbotcontrol;

import ch.ergon.xcsbotcontrol.api.XcodeApi;
import ch.ergon.xcsbotcontrol.api.XcodeCredentials;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static ch.ergon.xcsbotcontrol.BotController.Action.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BotControllerTest {

	@Mock(lenient = true)
	XcodeApi fakeXcodeApi;

	@Mock(lenient = true)
	Keychain fakeKeychain;

	private BotController sut;

	@BeforeEach
	void init() throws IOException {
		sut = new BotController();
		sut.xcodeApi = fakeXcodeApi;
		sut.keychain = fakeKeychain;
		XcodeCredentials credentials = new XcodeCredentials("user", "pass");
		when(fakeKeychain.findItem(anyString())).thenReturn(credentials);
	}

	@Test
	void createWithoutConfigFileShouldFail() {
		sut.action = CREATE;
		Integer exitCode = sut.call();
		assertNotEquals(exitCode, 0);
	}

	@Test
	void createWithMoreThanOneConfigFileAndBotNameOverrideShouldFail() {
		sut.action = CREATE;
		Integer exitCode = sut.call();
		assertNotEquals(exitCode, 0);
	}

	@Test
	void integrateWithBotNameAndHostProvidedShouldIgnoreConfigFile() throws IOException {
		sut.action = INTEGRATE;
		sut.botName = "bbbot";
		sut.host = "host.example.com";
		sut.configFiles = ImmutableList.of("file1.json");
		Integer exitCode = sut.call();
		assertEquals(exitCode, 0);
		verify(fakeXcodeApi).startIntegrationByName(any(XcodeCredentials.class), eq("host.example.com"), eq("bbbot"));
	}
}
