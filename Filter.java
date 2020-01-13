
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
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
public class Filter {
	// define all your variables that you need to initialise through different tests
	private static ChromeDriver driver;
	private String expectedResult;
	private String actualResult;
	private WebElement element;
	private Select sc;
	private List<WebElement> elements;
	private int size;
	

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
	@SpiraTestCase(testCaseId = 15652)
	// 1. Open automationpractice.com and get a list of dress items for filtering
	public void openItemListTest() {

		driver.get("http://automationpractice.com/");

		// Wait maximum for 5 seconds for the next
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); 
		
		driver.findElement(By.xpath("//*[@id=\'block_top_menu\']/ul/li[2]/a")).click();

		// Wait maximum for 5 seconds for the next
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        
		expectedResult = "Dresses - My Store";
		actualResult = driver.getTitle();
		assertEquals(expectedResult, actualResult);

	}
    
	@Test
	@Order(2) 
	@SpiraTestCase(testCaseId = 15653)
	// 2. Put items in ascending order
	public void itemSortTest() {
	    
	    sc = new Select(driver.findElement(By.id("selectProductSort")));
	    sc.selectByValue("price:asc");
	    
	    // Wait maximum for 5 seconds for the next
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        
	    actualResult =  driver.findElement(By.xpath("//*[@id=\"center_column\"]/ul/li[1]/div/div[2]/div[1]/span")).getAttribute("innerHTML").trim();
	    expectedResult = "$16.40";
	    assertEquals(expectedResult, actualResult);
	}
	
	@Test
    @Order(3) 
    @SpiraTestCase(testCaseId = 15654)
    // 3.1 Composition selection checkbox
    public void itemFilterCompositionTest_checkbox() {
        
        // refresh page to reset filter
	    driver.navigate().refresh();
	    // Wait maximum for 5 seconds for the next
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        
        // tick "Polyester"
        element = driver.findElement(By.id("layered_id_feature_1"));
        element.click();
        
        
        assertTrue(element.isSelected());
	}
	
	@Test
    @Order(4) // << the order of the test, so this test-case is running as second
    @SpiraTestCase(testCaseId = 15656)
    // 3.2 Composition selection filter result
    public void itemFilterCompositionTest_result() {
        
	    
        // find number of items from filtered result
        elements = driver.findElements(By.cssSelector("ul.product_list.grid.row > li"));
        
        // actual size
        size = elements.size();
                    
        // expected size is 2
        assertEquals(2, size);
    }
	
	@Test
    @Order(5) // << the order of the test, so this test-case is running as second
    @SpiraTestCase(testCaseId = 15657)
        
    // 4.1 Style filter result, checkbox test
    public void styleFilterTest_checkBox() {
         
	    // refresh page to reset filter
        driver.navigate().refresh();
        // Wait maximum for 5 seconds for the next
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	    
        // tick Style "casual"
        element = driver.findElement(By.id("layered_id_feature_11"));
        element.click();
        
        
        assertTrue(element.isSelected());
       
    }
	
	@Test
    @Order(6)
    @SpiraTestCase(testCaseId = 15658)
    // 4.2 Style filter result, filtered result
    public void styleFilterTest_result() {
        
        
        // find number of items from filtered result
        elements = driver.findElements(By.cssSelector("ul.product_list.grid.row > li"));
        
        // actual size
        size = elements.size();
                    
        // expected size is 1
        assertEquals(1, size);
    }
	
	@Test
	@Order(7) // << the order of the test, so this test-case is running as second
	@SpiraTestCase(testCaseId = 15659)
	    
	// 5.1 Sub Category filter result
	public void subCategoryAcessTest_viaCheckBox() {
	        
	    // refresh page to reset filter
	    driver.navigate().refresh();  
	        
        // Wait maximum for 5 seconds for the next
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        
        // tick "summer dress"
        driver.findElement(By.id("layered_category_11")).click();
        
        // find number of items from filtered result
        elements = driver.findElements(By.cssSelector("ul.product_list.grid.row > li"));
        
        // actual size
        size = elements.size();
                    
        // expected size is 3
        assertEquals(3, size);
       
	}
	
	@Test
    @Order(8) // << the order of the test, so this test-case is running as second
    @SpiraTestCase(testCaseId = 15661)
    // 5.2 Sub Category filter result
    public void subCategoryAcessTest_viaLink() {
        
	    // refresh page to reset filter
        driver.navigate().refresh();  
        
        // access Summer Dress via direct image link
        driver.findElement(By.xpath("//*[@id=\"subcategories\"]/ul/li[3]/div[1]/a")).click();
        
        actualResult = driver.getTitle();
        expectedResult = "Summer Dresses - My Store";
        
        
        assertEquals(expectedResult, actualResult);
    }
	

	
	@AfterAll
	// closing or quitting the browser after the test
	public static void closeBrowser() {
		driver.close();
		// driver.quit();
	}
}
