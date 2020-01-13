
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
import org.openqa.selenium.interactions.Action;
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
		// releaseId = 7, testSetId = 1)
)

@TestMethodOrder(OrderAnnotation.class) // << this annotation is for using @order, or adding order to my test-cases
public class PlaceOrder {
	// define all your variables that you need to initialise through different tests
	private static ChromeDriver driver;
	private String expectedResult;
	private String actualResult;
	private WebElement element;
	
	

	@BeforeAll
	// setup my driver here through @BeforeAll, this method is running once before
	// all test-cases
	public static void setup() {
		
		// chromedriver must be replaced if it is not working or your operating system is not windows
		
		System.setProperty("Webdriver.chrome.driver", "chromedriver");
		

		driver = new ChromeDriver();
		
	}

	@Test
	@Order(1) // << the order of the test, so this test-case is running as first
	@SpiraTestCase(testCaseId = 15546)
	// 1. Open automationpractice.com and find a T-shirt to order
	public void openTShirtCategory() {

		driver.get("http://automationpractice.com/");

		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); // << I asked wait maximum for 5 seconds for the next
		driver.findElement(By.xpath("//*[@id=\'block_top_menu\']/ul/li[3]/a")).click();
		
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		expectedResult = "T-shirts - My Store";
		actualResult = driver.getTitle();
		assertEquals(expectedResult, actualResult);

	}
    
	@Test
	@Order(2) 
	@SpiraTestCase(testCaseId = 15547)
	// 2. Go to T-shirt item detail page
	public void openDetailPageTest() {
	    
	    driver.findElement(By.xpath("//*[@id=\'center_column\']/ul/li/div/div[2]/h5/a")).click();
	    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	    
	    expectedResult = "Faded Short Sleeve T-shirts - My Store";
	    actualResult = driver.getTitle();	    
	    
	    assertEquals(expectedResult, actualResult);
	    
	}
	
	@Test
    @Order(3) 
    @SpiraTestCase(testCaseId = 15548)
    // 3. add item to shopping card
    public void addToCartTest() throws InterruptedException {
        
        driver.findElement(By.name("Submit")).click();
        
        // Force to wait for AJAX status change from DOM
        Thread.sleep(3500);
        
        expectedResult = "1";
        actualResult = driver.findElement(By.xpath("//*[@id=\'layer_cart\']/.//span[contains(@class, \'ajax_cart_quantity\')]")).getAttribute("innerHTML");
        assertEquals(expectedResult, actualResult);
	}

	@Test
    @Order(4) 
    @SpiraTestCase(testCaseId = 15549)
    // 4. Proceed to order with the item from cart
    public void proceedToOrderTest() {       
        
	    // click proceed to order button
        driver.findElement(By.xpath("//*[@id=\'layer_cart\']/div[1]/div[2]/div[4]/a")).click();
        
        // wait for new page loading
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        
        
        actualResult = driver.getTitle();
        expectedResult = "Order - My Store";
        assertEquals(expectedResult, actualResult);
    }
	
	@Test
    @Order(5) 
    @SpiraTestCase(testCaseId = 15550)
    // 5. Proceed to Check-out
    public void proceedToCheckoutTest() {
        
        // click proceed to checkout button
        driver.findElement(By.xpath("//*[@id=\'center_column\']/p[2]/a[1]")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        
        
        actualResult = driver.getTitle();
        expectedResult = "Login - My Store";
        assertEquals(expectedResult, actualResult);
    }
	
	@Test
    @Order(6) 
    @SpiraTestCase(testCaseId = 15551)
    // 6. Login required to checkout
    public void proceedToLoginTest() {
        
        
	    // enter valid email
        driver.findElement(By.id("email")).sendKeys("cevegoxa@amailr.net");               
        
        // enter valid password       
        driver.findElement(By.id("passwd")).sendKeys("7777777");
        
        // click login button
        driver.findElement(By.id("SubmitLogin")).click();
        
        // wait for page to redirect
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        
        actualResult = driver.getTitle();
        expectedResult = "Order - My Store";
        assertEquals(expectedResult, actualResult);
    }
	
	@Test
    @Order(7) 
    @SpiraTestCase(testCaseId = 15552)
    // 7. Confirm addresses 
    public void processAddressTest() {
        
	    driver.findElement(By.name("processAddress")).click();
        
	    // wait for new page loading
	    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        
        
        actualResult = driver.findElement(By.xpath("//*[@id=\"form\"]/div/p[1]")).getText();
        expectedResult = "Terms of service";
        assertEquals(expectedResult, actualResult);
    }
	
	@Test
    @Order(8) 
    @SpiraTestCase(testCaseId = 15553)
    // 8. Agree Terms of Services and proceed
    public void agreeServiceTermTest() throws InterruptedException {
        
        
        element = driver.findElement(By.id("cgv"));
       
        element.click();
        
        assertTrue(element.isSelected());
    }
	
	@Test
    @Order(9) 
    @SpiraTestCase(testCaseId = 15554)
    // 10. Confirm shipping/carrier cost
    public void processShippingTest() {
        
        
        driver.findElement(By.name("processCarrier")).click();
       
        actualResult = driver.findElement(By.xpath("//*[@id=\'cart_summary\']/tfoot/tr[5]/td[1]/span")).getAttribute("innerHTML");
        expectedResult = "Total";
        
        assertEquals(expectedResult, actualResult);
     
    }
	
	@Test
    @Order(10) 
    @SpiraTestCase(testCaseId = 15556)
    // 10. select payment method
    public void checkPaymentTest() {
        
        // pay by check
        driver.findElement(By.xpath("//*[@id=\'HOOK_PAYMENT\']/div[2]/div/p/a")).click();
       
        actualResult = driver.findElement(By.xpath("//*[@id=\'center_column\']/form/div/h3")).getAttribute("innerHTML");
        expectedResult = "Check payment";
        
        assertEquals(expectedResult, actualResult);
      
    }
	
	@Test
    @Order(11) 
    @SpiraTestCase(testCaseId = 15559)
    // 11. Receive order confirmation 
    public void orderConfirmationTest() {
        
        // click proceed button
        driver.findElement(By.xpath("//*[@id=\'cart_navigation\']/button")).click();
       
        actualResult = driver.getTitle();
        expectedResult = "Order confirmation - My Store";
        
        assertEquals(expectedResult, actualResult);
      
    }
	
	
	@Test
    @Order(12) 
    @SpiraTestCase(testCaseId = 15562)
    // 12. confirm order at order history/details page
    public void goToOrdersTest() {
        
        // go to orders page
        driver.findElement(By.xpath("//*[@id=\'center_column\']/p[2]/a")).click();
       
        actualResult = driver.findElement(By.xpath("//*[@id=\'order-list\']/tbody/tr[1]/td[3]")).getAttribute("data-value");
        expectedResult = "18.51";
        
        assertEquals(expectedResult, actualResult);
      
    }
	
	@AfterAll
	// closing or quitting the browser after the test
	public static void closeBrowser() {
		driver.close();
		
	}
}
