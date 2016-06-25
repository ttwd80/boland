package web;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.MarionetteDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class AbstractWebIntegrationTest {
	protected WebDriver webDriver;
	protected String baseUrl = "http://localhost:58080";

	@Before
	public void init() {
		webDriver = createWebDriver();
		final Options options = webDriver.manage();
		final Window window = options.window();
		window.maximize();
	}

	@After
	public void cleanUp() {
		webDriver.quit();
	}

	private WebDriver createWebDriver() {
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

	private String readKey() {
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

		void sleepIfNeeded();
	}

	public static class CreatePhantomJSDriverStrategy implements CreateWebDriverStrategy {

		@Override
		public WebDriver create() {
			final WebDriver webDriver = new PhantomJSDriver();
			return webDriver;
		}

		@Override
		public void sleepIfNeeded() {
		}
	}

	public static class CreateFirefoxDriverStrategy implements CreateWebDriverStrategy {

		@Override
		public WebDriver create() {
			return createRemote();
		}

		protected WebDriver createRemote() {
			final DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			capabilities.setCapability("marionette", "true");
			final WebDriver webDriver = new MarionetteDriver();
			return webDriver;

		}

		protected WebDriver createFirefox() {
			try {
				final DesiredCapabilities capabilities = DesiredCapabilities.firefox();
				capabilities.setCapability("marionette", "true");
				final WebDriver webDriver = new FirefoxDriver(capabilities);
				return webDriver;
			} catch (final RuntimeException e) {
				throw e;
			} catch (final Exception e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public void sleepIfNeeded() {
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (final InterruptedException e) {
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

		@Override
		public void sleepIfNeeded() {
		}
	}
}
