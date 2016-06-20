package web;

import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

public class AbstractWebIntegrationTest {
	protected WebDriver webDriver;
	protected String baseUrl = "http://localhost:58080";

	@Before
	public void init() {
		createPhantomJSDriver();
	}

	protected void createPhantomJSDriver() {
		webDriver = new PhantomJSDriver();
	}

	protected void createChromeDriver() {
		webDriver = new ChromeDriver();
	}

	protected void createFirefoxDriver() {
		webDriver = new FirefoxDriver();
	}
}
