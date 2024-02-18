
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class is used to test the Test case 3: Verify different prices
 */
public class TestCaseThree {
	/**
	 * Member tableKey stores key which can identify a row in a table uniquely
	 */
	private String[] tableKey = { "Stuffed Frog", "Fluffy Bunny", "Valentine Bear" };

	/**
	 * Method clickButton clicks button several times
	 */
	private void clickButton(WebDriver driver, String idVal, int numberOfClicks) {
		WebElement button = driver.findElement(By.cssSelector(idVal));
		for (int i = 0; i < numberOfClicks; i++) {
			button.click();
		}
	}

	/**
	 * Member hashMap stores how many times the action button should be clicked
	 */
	@SuppressWarnings("serial")
	private HashMap<String, Integer> hashMap = new HashMap<String, Integer>() {
		{
			put("#product-2 a", 2);
			put("#product-4 a", 5);
			put("#product-7 a", 3);
		}
	};

	/**
	 * hashMapSubTotal stores the columnIndex of Subtotal in the table
	 */
	@SuppressWarnings("serial")
	private HashMap<String, Integer> hashMapSubTotal = new HashMap<String, Integer>() {
		{
			put("Subtotal", 3);
		}
	};
	/**
	 * Member tableRow stores row values and their corresponding column indexes.
	 */
	@SuppressWarnings("serial")
	private HashMap[] tableRow = { new HashMap<String, Integer>() {
		{
			put("Stuffed Frog", 0);
			put("$10.99", 1);
			put("$21.98", 3);
		}
	}, new HashMap<String, Integer>() {
		{
			put("Fluffy Bunny", 0);
			put("$9.99", 1);
			put("$49.95", 3);
		}
	}, new HashMap<String, Integer>() {
		{
			put("Valentine Bear", 0);
			put("$14.99", 1);
			put("$44.97", 3);
		}
	} };

	/**
	 * Method assertTableRow asserts Item, price and subtotal in the table
	 */
	private void assertTableRow(WebElement table) {
		for (int i = 0; i < tableRow.length; i++) {
			WebElement row = table.findElement(By.xpath("//tr[contains(., '" + tableKey[i] + "')]"));
			java.util.List<WebElement> cells = row.findElements(By.tagName("td"));
			@SuppressWarnings("unchecked")
			HashMap<String, Integer> temp = tableRow[i];
			for (HashMap.Entry<String, Integer> entry : temp.entrySet()) {
				String actualCellValue = cells.get(entry.getValue()).getText();
				String expectedCellValue = entry.getKey();
				assertEquals(actualCellValue, expectedCellValue);
			}
		}
	}

	/**
	 * Method assertSumofSubTotalEqualsToTotal asserts the sumOfSubtotal equals to
	 * Total
	 */
	private void assertSumofSubTotalEqualsToTotal(WebDriver driver, WebElement table) {
		List<WebElement> rows = table.findElements(By.cssSelector("tbody tr"));
		float sumOfSubTotal = 0;
		// Iterate through rows and get the cell value from the specified column
		for (WebElement row : rows) {
			List<WebElement> cells = row.findElements(By.tagName("td"));
			String cellValue = cells.get(hashMapSubTotal.get("Subtotal")).getText();
			sumOfSubTotal += Float.parseFloat(cellValue.substring(1));
		}
		String formattedSumValue = String.format("%.1f", sumOfSubTotal);
		WebElement Total = driver.findElement(By.cssSelector(".total"));
		String TotalValue = Total.getText().replaceAll("[^\\d.]+", "");
		assertEquals(TotalValue, formattedSumValue);
	}

	/**
	 * Method differentBrowsersTest runs test on different browsers
	 */
	private void differentBrowsersTest(WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		// Navigate to Url
		try {
			driver.get("https://jupiter.cloud.planittesting.com/");

			// Click on the element
			WebElement submitButton = driver.findElement(By.linkText("Start Shopping »"));
			submitButton.click();

			for (HashMap.Entry<String, Integer> entry : hashMap.entrySet()) {
				clickButton(driver, entry.getKey(), entry.getValue());
			}
			WebElement cart = driver.findElement(By.cssSelector("#nav-cart a"));
			cart.click();

			WebElement table = driver.findElement(By.cssSelector(".table"));

			assertTableRow(table);

			assertSumofSubTotalEqualsToTotal(driver, table);

			Thread.sleep(5000);
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
		optionsChrome.addArguments("disable-infobars"); // disabling infobars
		optionsChrome.addArguments("--disable-extensions"); // disabling extensions
		optionsChrome.addArguments("--disable-gpu"); // applicable to windows os only
		optionsChrome.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
		optionsChrome.addArguments("--no-sandbox"); // Bypass OS security model
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