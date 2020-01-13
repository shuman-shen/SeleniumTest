
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.inflectra.spiratest.addons.junitextension.SpiraTestCase;
import com.inflectra.spiratest.addons.junitextension.SpiraTestConfiguration;


@SpiraTestConfiguration(
        // following are REQUIRED
        //url = "URL", 
        //login = "ID", 
        //rssToken = "RSS_TOKEN", 
        //projectId = NUM_OF_PROJECT_ID
        // following are OPTIONAL
        // releaseId = 7, testSetId = 1
)

@TestMethodOrder(OrderAnnotation.class) // << this annotation is for using @order, or adding order to my test-cases
public class Login {
    
    // define all your variables that you need to initialise through different tests
    private static ChromeDriver driver;
    private String expectedResult;
    private String actualResult;
    private WebElement element;

    @BeforeAll
    // setup my driver here through @BeforeAll, this method is running once before
    // all test-cases
    public static void setup() {

        // chromedriver must be replaced if it is not working or your operating system
        // is not windows

        System.setProperty("Webdriver.chrome.driver", "chromedriver");

        driver = new ChromeDriver();

    }

    @Test
    @Order(1) // << the order of the test, so this test-case is running as first
    @SpiraTestCase(testCaseId = 15509)
    // 1. open automationpractice.com page
    public void openWebsiteTest() {

        driver.get("http://automationpractice.com/");

        // wait for maximum 5 seconds
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); 
        
        expectedResult = "My Store";
        actualResult = driver.getTitle();
        assertEquals(expectedResult, actualResult);

    }

    @Test
    @Order(2) // << the order of the test, so this test-case is running as second
    @SpiraTestCase(testCaseId = 15510)
    // 2. Click sign-in button and check the title of the sign-in page
    public void signInButtonTest() {

        driver.findElement(By.xpath("//*[@id=\'header\']/div[2]/div/div/nav/div[1]/a")).click();
        actualResult = driver.getTitle();
        expectedResult = "Login - My Store";
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @Order(3) // << the order of the test, so this test-case is running as second
    @SpiraTestCase(testCaseId = 15511)
    // 3. Enter email and text to the login fields and test the input content
    public void logInFieldInputTest() {

        // email field
        element = driver.findElement(By.id("email"));
        element.sendKeys("abc");
        expectedResult = "abc";
        actualResult = element.getAttribute("value");
        assertEquals(expectedResult, actualResult);

        // password field
        element = driver.findElement(By.id("passwd"));
        element.sendKeys("7777777");
        expectedResult = "7777777";
        actualResult = element.getAttribute("value");
        assertEquals(expectedResult, actualResult);

    }

    @Test
    @Order(4) // << the order of the test, so this test-case is running as second
    @SpiraTestCase(testCaseId = 15512)
    // 4.1 Click the log-in button and test with invalid email/password combination
    public void logInFieldTest_invalidEmail() {

        driver.findElement(By.id("SubmitLogin")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);


        element = driver.findElement(By.xpath("//*[@id=\'center_column\']/div[1]/p"));

        actualResult = element.getText();
        expectedResult = "There is 1 error";

        assertEquals(expectedResult, actualResult);

    }

    @Test
    @Order(5) // << the order of the test, so this test-case is running as second
    @SpiraTestCase(testCaseId = 15513)
    // 4.2 Click the log-in button and test with empty password
    public void logInFieldTest_noPassword() {

        // enter valid email
        driver.findElement(By.id("email")).clear();
        driver.findElement(By.id("email")).sendKeys("cevegoxa@amailr.net");
        // enter empty password
        driver.findElement(By.id("passwd")).clear();

        // click login button
        driver.findElement(By.id("SubmitLogin")).click();
        // wait for page to redirect
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        element = driver.findElement(By.xpath("//*[@id=\'center_column\']/div[1]/p"));

        actualResult = element.getText();
        expectedResult = "There is 1 error";

        assertEquals(expectedResult, actualResult);

    }

    @Test
    @Order(6) // << the order of the test, so this test-case is running as second
    @SpiraTestCase(testCaseId = 15514)
    // 4.3 Click the log-in button and test with invalid email/password combination
    public void logInFieldTest_validEmail() {

        // enter valid email
        driver.findElement(By.id("email")).clear();
        driver.findElement(By.id("email")).sendKeys("cevegoxa@amailr.net");
        
        // enter valid password
        driver.findElement(By.id("passwd")).clear();
        driver.findElement(By.id("passwd")).sendKeys("7777777");
        
        // click login button
        driver.findElement(By.id("SubmitLogin")).click();
        
        // wait for page to redirect
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        actualResult = driver.getTitle();
        expectedResult = "My account - My Store";

        assertEquals(expectedResult, actualResult);

    }

    @AfterAll
    // closing or quitting the browser after the test
    public static void closeBrowser() {
        driver.close();
       
    }
}
