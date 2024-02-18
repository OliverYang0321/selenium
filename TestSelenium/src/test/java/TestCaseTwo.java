
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class is used to test Test case 2 Verify successful submit message
 */
public class TestCaseTwo {
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

			WebElement fornameInput = driver.findElement(By.id("forename"));
			fornameInput.sendKeys("testForname");
			WebElement emailInput = driver.findElement(By.id("email"));
			emailInput.sendKeys("testForname@gmail.com");
			WebElement messageInput = driver.findElement(By.id("message"));
			messageInput.sendKeys("Hello World");

			WebElement submitButton = driver.findElement(By.linkText("Submit"));
			submitButton.click();

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
			WebElement element = wait
					.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.alert-success")));
			String successfulMessage = element.getText();
			assertEquals(successfulMessage, "Thanks testForname, we appreciate your feedback.");

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