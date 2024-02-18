
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class is used to test the second part of Test case 1: Verify error
 * messages gone after populating any mandatory fields
 */
public class TestCaseOnePartTwo {
	/**
	 * Member stylesSelector stores id selector for error message elements
	 */
	private String[] stylesSelector = { "forename-err", "email-err", "message-err" };

	/**
	 * Method assertionContent asserts the content of header message
	 */
	private void assertionHeadMessageContent(WebDriver driver, String idVal, String content) {
		String headMessage = driver.findElement(By.id(idVal)).getText();
		assertEquals(headMessage, content);
	}

	/**
	 * Method differentBrowsersTest runs test on different browsers
	 */
	private void differentBrowsersTest(WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		// Navigate to Url
		try {
			driver.get("https://jupiter.cloud.planittesting.com/");

			// Click on the element
			WebElement contactNavItem = driver.findElement(By.id("nav-contact"));
			contactNavItem.click();
			WebElement submitButton = driver.findElement(By.linkText("Submit"));
			submitButton.click();

			WebElement fornameInput = driver.findElement(By.id("forename"));
			fornameInput.sendKeys("testForname");
			WebElement emailInput = driver.findElement(By.id("email"));
			emailInput.sendKeys("testForname@gmail.com");
			WebElement messageInput = driver.findElement(By.id("message"));
			messageInput.sendKeys("Hello World");

			for (String selector : stylesSelector) {
				List<WebElement> elements = driver.findElements(By.id(selector));
				assertTrue(elements.isEmpty());
			}

			assertionHeadMessageContent(driver, "header-message", "We welcome your feedback - tell it how it is.");

			WebElement headerMessage = driver.findElement(By.cssSelector("#header-message div"));
			assertFalse(headerMessage.getAttribute("class").contains("alert-error"));

		} catch (org.openqa.selenium.NoSuchElementException e) {
			// Handle the exception
			System.out.println("Element not found: " + e.getMessage());

		} catch (Exception e) {
			// Handle any other exceptions
			System.out.println("An unexpected error occurred: " + e.getMessage());

		} finally {
			// Close the browser in the finally block to ensure it's closed even if an
			// exception occurs
			driver.quit();
		}
	}

	@Test
	public void interactWithElementsOnChrome() {

		// Test on Chrome
		ChromeOptions optionsChrome = new ChromeOptions();
		optionsChrome.addArguments("--start-maximized"); // open Browser in maximized mode
		optionsChrome.addArguments("--headless=new");
		WebDriver driverChrome = new ChromeDriver(optionsChrome);
		differentBrowsersTest(driverChrome);
	}

	@Test
	public void interactWithElementsOnFireFox() {

		// Test on FireFox
		FirefoxOptions optionsFireFox = new FirefoxOptions();
    	optionsFireFox.addArguments("--headless");
		WebDriver driverFireFox = new FirefoxDriver(optionsFireFox);
		differentBrowsersTest(driverFireFox);

	}

}