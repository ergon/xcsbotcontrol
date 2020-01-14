package ch.ergon.xcsbotcontrol.api;

import ch.ergon.xcsbotcontrol.config.BotCreationSite;
import ch.ergon.xcsbotcontrol.api.methods.*;
import ch.ergon.xcsbotcontrol.api.model.*;
import com.google.common.io.CharStreams;
import com.google.gson.JsonSyntaxException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class XcodeRequests implements XcodeApi {
	@Override
	public void createBot(BotCreationSite site) {
		HttpsURLConnection connection = null;
		try {
			connection = openAuthenticatedSession(site.getCredentials(), site.getXcodeServerHost(), HttpMethod.POST, "bots");

			OutputStreamWriter outputStream = new OutputStreamWriter(connection.getOutputStream());
			outputStream.write(site.createBotRequestBody());
			outputStream.close();
			int responseCode = connection.getResponseCode();
			if (responseCode != 201) {
				throw new XcodeRequestException(String.format("Failed to create bot with response code %d", responseCode));
			}
		} catch (IOException e) {
			throw new XcodeRequestException(String.format("Failed to create bot for bot %s", site.getBotName()), e);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	@Override
	public List<Device> fetchDevices(XcodeCredentials credentials, String host) {
		HttpsURLConnection connection = null;
		try {
			connection = openAuthenticatedSession(credentials, host, HttpMethod.GET, "devices");

			InputStream inputStream = connection.getInputStream();
			int responseCode = connection.getResponseCode();
			if (responseCode != 200) {
				throw new XcodeRequestException(String.format("Failed to fetch devices with response code %d", responseCode));
			}

			try (InputStreamReader inStreamReader = new InputStreamReader(inputStream)) {
				String inAsString = CharStreams.toString(inStreamReader);
				List<Device> devices = GetDevicesResponse.fromJson(inAsString).getResults();
				return devices.stream()
						.sorted(Device.byOsVersion().reversed())
						.collect(toList());
			}
		} catch (IOException e) {
			throw new XcodeRequestException("Failed to fetch devices", e);
		} catch (JsonSyntaxException e) {
			throw new XcodeRequestException("Failed to parse fetch devices response", e);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	@Override
	public List<DevicePlatform> fetchPlatforms(XcodeCredentials credentials, String host) {
		HttpsURLConnection connection = null;
		try {
			connection = openAuthenticatedSession(credentials, host, HttpMethod.GET, "platforms");

			InputStream inputStream = connection.getInputStream();
			int responseCode = connection.getResponseCode();
			if (responseCode != 200) {
				throw new XcodeRequestException(String.format("Failed to fetch platforms with response code %d", responseCode));
			}

			try (InputStreamReader inStreamReader = new InputStreamReader(inputStream)) {
				String inAsString = CharStreams.toString(inStreamReader);
				return GetPlatformsResponse.fromJson(inAsString).getResults();
			}
		} catch (IOException e) {
			throw new XcodeRequestException("Failed to fetch platforms", e);
		} catch (JsonSyntaxException e) {
			throw new XcodeRequestException("Failed to parse fetch platforms response", e);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	@Override
	public List<Toolchain> fetchToolchains(XcodeCredentials credentials, String host) {
		HttpsURLConnection connection = null;
		try {
			connection = openAuthenticatedSession(credentials, host, HttpMethod.GET, "toolchains");

			InputStream inputStream = connection.getInputStream();
			int responseCode = connection.getResponseCode();
			if (responseCode != 200) {
				throw new XcodeRequestException(String.format("Failed to fetch toolchains with response code %d", responseCode));
			}

			try (InputStreamReader inStreamReader = new InputStreamReader(inputStream)) {
				String inAsString = CharStreams.toString(inStreamReader);
				System.out.println(inAsString);
				return GetToolchainsResponse.fromJson(inAsString).getResults();
			}
		} catch (IOException e) {
			throw new XcodeRequestException("Failed to fetch toolchains", e);
		} catch (JsonSyntaxException e) {
			throw new XcodeRequestException("Failed to parse fetch toolchains response", e);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	@Override
	public void startIntegrationByName(XcodeCredentials credentials, String host, String botName) {
		Optional<String> botId = findBotIdByName(host, botName);
		if (!botId.isPresent()) {
			throw new XcodeRequestException(String.format("Could not find bot %s", botName));
		}
		startIntegration(credentials, host, botId.get());
	}

	@Override
	public void startIntegration(XcodeCredentials credentials, String host, String botId) {
		HttpsURLConnection connection = null;
		try {
			connection = openAuthenticatedSession(credentials, host, HttpMethod.POST, "bots/" + encodeBotId(botId) + "/integrations");
			OutputStreamWriter outputStream = new OutputStreamWriter(connection.getOutputStream());
			outputStream.write("{}"); // would be possible to enforce clean with { shouldClean: true }
			outputStream.close();
			int responseCode = connection.getResponseCode();
			if (responseCode != 201) {
				throw new XcodeRequestException(String.format("Failed to start integration for bot [%s] with response code %d", botId, responseCode));
			}
		} catch (IOException e) {
			throw new XcodeRequestException("Failed to start integraiton of bot", e);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	@Override
	public void deleteBotByName(XcodeCredentials credentials, String host, String botName) {
		Optional<String> botId = findBotIdByName(host, botName);
		if (!botId.isPresent()) {
			throw new XcodeRequestException(String.format("Failed to delete bot for bot %s (could not be found on Xcode Server)", botName));
		}
		deleteBot(credentials, host, botId.get());
	}

	@Override
	public void deleteBot(XcodeCredentials credentials, String host, String botId) {
		HttpsURLConnection connection = null;
		try {
			connection = openAuthenticatedSession(credentials, host, HttpMethod.DELETE, "bots/" + encodeBotId(botId));
			int responseCode = connection.getResponseCode();
			if (responseCode != 204) {
				throw new XcodeRequestException(String.format("Failed to delete bot [%s] returned response code %d", botId, responseCode));
			}
		} catch (IOException e) {
			throw new XcodeRequestException(String.format("Failed to delete bot [%s]", botId), e);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	private String encodeBotId(String botId) {
		try {
			return URLEncoder.encode(botId, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new XcodeRequestException("Failed to encode bot ID" + botId, e);
		}
	}

	@Override
	public Optional<String> findBotIdByName(String host, String botName) {
		List<GetBotContainer> allBots = fetchBots(host);
		return allBots.stream()
				.filter(b -> b.getName().equals(botName))
				.map(GetBotContainer::getId)
				.findFirst();
	}

	@Override
	public List<GetBotContainer> fetchBots(String host) {
		HttpsURLConnection connection = null;
		try {
			connection = openSession(host, HttpMethod.GET, "bots");
			InputStream inputStream = connection.getInputStream();

			int responseCode = connection.getResponseCode();
			if (responseCode != 200) {
				throw new XcodeRequestException(String.format("Failed to fetch bots with response code %d", responseCode));
			}
			try (InputStreamReader inStreamReader = new InputStreamReader(inputStream)) {
				String inAsString = CharStreams.toString(inStreamReader);
				return GetBotResponse.fromJson(inAsString).getResults();
			}
		} catch (IOException e) {
			throw new XcodeRequestException("Failed to fetch bots", e);
		} catch (JsonSyntaxException e) {
			throw new XcodeRequestException("Failed to parse fetch bots response", e);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	private HttpsURLConnection openAuthenticatedSession(XcodeCredentials credentials, String host, HttpMethod method, String path) throws IOException {
		HttpsURLConnection connection = openSession(host, method, path);
		connection.setRequestProperty("Authorization", "Basic " + getEncodedAuth(credentials));
		return connection;
	}

	private HttpsURLConnection openSession(String host, HttpMethod method, String path) throws IOException {
		String xcodeApiUrl = "https://" + host + ":20343/api/" + path;
		URL url = new URL(xcodeApiUrl);
		trustAllCerts();

		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		connection.setRequestMethod(method.name());
		connection.setRequestProperty("Content-Type", "application/json");
		if (method == HttpMethod.POST) {
			connection.setDoOutput(true);
		}
		return connection;
	}

	private void trustAllCerts() {
		TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager(){
			public X509Certificate[] getAcceptedIssuers(){return null;}
			public void checkClientTrusted(X509Certificate[] certs, String authType){}
			public void checkServerTrusted(X509Certificate[] certs, String authType){}
		}};

		// Install the all-trusting trust manager
		try {
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, trustAllCerts, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			HttpsURLConnection.setDefaultHostnameVerifier(new NoopHostnameVerifier());
		} catch (Exception e) {
			System.out.println("Failed to install trust manager.");
		}
	}

	private String getEncodedAuth(XcodeCredentials credentials) {
		String authUsername = credentials.getUsername();
		String authPassword = credentials.getPassword();
		String authString = authUsername + ":" + authPassword;
		return new String(Base64.getEncoder().encode(authString.getBytes()));
	}

	enum HttpMethod {
		GET,
		POST,
		DELETE
	}
}
