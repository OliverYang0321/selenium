

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InteractionTest{

    @Test
    public void interactWithElements() {
    	ChromeOptions options = new ChromeOptions();
    	options.addArguments("start-maximized"); // open Browser in maximized mode
    	options.addArguments("disable-infobars"); // disabling infobars
    	options.addArguments("--disable-extensions"); // disabling extensions
    	options.addArguments("--disable-gpu"); // applicable to windows os only
    	options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
    	options.addArguments("--no-sandbox"); // Bypass OS security model
    	options.addArguments("--headless");
    	WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        // Navigate to Url
        driver.get("https://www.selenium.dev/selenium/web/inputs.html");

	    // Click on the element 
        WebElement checkInput=driver.findElement(By.name("checkbox_input"));
        checkInput.click();
        Boolean isChecked=checkInput.isSelected();
        assertEquals(isChecked,false);

        //SendKeys
        // Clear field to empty it from any previous data
        WebElement emailInput=driver.findElement(By.name("email_input"));
        emailInput.clear();
	    //Enter Text
        String email="admin@localhost.dev";
	    emailInput.sendKeys(email);
        //Verify
        String data=emailInput.getAttribute("value");
        assertEquals(data,email);


        //Clear Element
        // Clear field to empty it from any previous data
        emailInput.clear();
        data=emailInput.getAttribute("value");
        assertEquals(data, ""); 

        driver.quit();
    }

}