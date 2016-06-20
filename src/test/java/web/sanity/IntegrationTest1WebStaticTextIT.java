package web.sanity;

import static org.hamcrest.core.IsEqual.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import web.AbstractWebIntegrationTest;

public class IntegrationTest1WebStaticTextIT extends AbstractWebIntegrationTest {

	@Test
	public void test1StaticTextAfrica() {
		webDriver.get(baseUrl + "/test/1-test-static-text.html");
		final String selector = "div div ol li span.africa";
		final List<WebElement> items = webDriver.findElements(By.cssSelector(selector));
		assertThat(items.size(), equalTo(2));
	}

	@Test
	public void test1StaticTextAsia() {
		webDriver.get(baseUrl + "/test/1-test-static-text.html");
		final String selector = "div div ol li span.asia";
		final List<WebElement> items = webDriver.findElements(By.cssSelector(selector));
		assertThat(items.size(), equalTo(4));
	}

	@Test
	public void test1StaticTextAntartica() {
		webDriver.get(baseUrl + "/test/1-test-static-text.html");
		final String selector = "div div ol li span.antartica";
		final List<WebElement> items = webDriver.findElements(By.cssSelector(selector));
		assertThat(items.size(), equalTo(0));
	}
}
