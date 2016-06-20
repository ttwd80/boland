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
		webDriver.get(baseUrl + "/test/test/1-test-static-text.html");
		final List<WebElement> items = webDriver.findElements(By.cssSelector("div div ol lo.africa"));
		assertThat(items.size(), equalTo(2));
	}

	@Test
	public void test1StaticTextAsia() {
		webDriver.get(baseUrl + "/test/test/1-test-static-text.html");
		final List<WebElement> items = webDriver.findElements(By.cssSelector("div div ol lo.asia"));
		assertThat(items.size(), equalTo(4));
	}
}
