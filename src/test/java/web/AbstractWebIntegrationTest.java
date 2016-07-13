package web;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.MarionetteDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AbstractWebIntegrationTest {
	protected WebDriver webDriver = null;
	protected String firstHandle = null;
	protected String baseUrl = "http://localhost:58080";

	@Before
	public void init() {
		webDriver = createWebDriver();
	}

	@After
	public void cleanUp() {
		webDriver.quit();
	}

	private static WebDriver createWebDriver() {
		final Map<String, CreateWebDriverStrategy> map = new LinkedHashMap<>();
		map.put("firefox", new CreateFirefoxDriverStrategy());
		map.put("chrome", new CreateChromeDriverStrategy());
		map.put("phantomjs", new CreatePhantomJSDriverStrategy());
		final String key = readKey();
		final CreateWebDriverStrategy createWebDriverStrategy = map.get(key);
		if (createWebDriverStrategy != null) {
			return createWebDriverStrategy.create();
		} else {
			throw new RuntimeException("no browser setting detected in browser.txt");
		}
	}

	private static String readKey() {
		try {
			final File file = new File("browser.txt");
			final byte[] content = FileUtils.readFileToByteArray(file);
			return new String(content);
		} catch (final IOException e) {
			return "phantomjs";
		}
	}

	public static interface CreateWebDriverStrategy {
		WebDriver create();
	}

	public static class CreatePhantomJSDriverStrategy implements CreateWebDriverStrategy {

		@Override
		public WebDriver create() {
			final WebDriver webDriver = new PhantomJSDriver();
			return webDriver;
		}
	}

	public static class CreateFirefoxDriverStrategy implements CreateWebDriverStrategy {

		@Override
		public WebDriver create() {
			return createRemote();
		}

		protected WebDriver createMarionette() {
			final DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			capabilities.setCapability("marionette", "true");
			final WebDriver webDriver = new MarionetteDriver(null, capabilities, 25_600);
			return webDriver;
		}

		protected WebDriver createRemote() {
			try {
				final URL url = new URL("http://localhost:25600");
				final DesiredCapabilities capabilities = DesiredCapabilities.firefox();
				capabilities.setCapability("marionette", true);
				final WebDriver webDriver = new RemoteWebDriver(url, capabilities);
				return webDriver;
			} catch (final MalformedURLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static class CreateChromeDriverStrategy implements CreateWebDriverStrategy {

		@Override
		public WebDriver create() {
			final ChromeDriver webDriver = new ChromeDriver();
			return webDriver;
		}

	}
}
