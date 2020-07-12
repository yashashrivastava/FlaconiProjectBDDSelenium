package context;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestContext {

    WebDriver driver;

    public TestContext(){
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chrome/chromedriver");
        driver = new ChromeDriver();
    }

    public WebDriver getDriver() {
        return driver;
    }
}
