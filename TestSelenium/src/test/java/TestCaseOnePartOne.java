
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class is used to test the first part of Test case 1: Verify error
 * messages after clicking submit button directly without populating any
 * mandatory fields
 */
public class TestCaseOnePartOne {
	@SuppressWarnings("serial")
	/**
	 * Member hashMap stores the pair of the id and the text of the error element
	 */
	private HashMap<String, String> hashMap = new HashMap<String, String>() {
		{
			put("forename-err", "Forename is required");
			put("email-err", "Email is required");
			put("message-err", "Message is required");
			put("header-message",
					"We welcome your feedback - but we won't get it unless you complete the form correctly.");
		}
	};
	/**
	 * Member stylesSelector stores the id of field in the Form
	 */
	private String[] stylesSelector = { "email-group", "message-group" };

	/**
	 * Method assertionContent asserts the content of error message
	 */
	private void assertionContent(WebDriver driver, String idVal, String content) {
		String errorForeName = driver.findElement(By.id(idVal)).getText();
		assertEquals(errorForeName, content);
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

			for (HashMap.Entry<String, String> entry : hashMap.entrySet()) {
				assertionContent(driver, entry.getKey(), entry.getValue());
			}

			for (String selector : stylesSelector) {
				assertTrue(driver.findElement(By.id(selector)).getAttribute("class").contains("error"));
			}

			WebElement headerMessage = driver.findElement(By.cssSelector("#header-message div"));
			assertTrue(headerMessage.getAttribute("class").contains("alert-error"));
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
		optionsChrome.addArguments("start-maximized"); // open Browser in maximized mode
		optionsChrome.addArguments("--window-size=1920,1080"); // open Browser in maximized mode
		optionsChrome.addArguments("--headless");
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