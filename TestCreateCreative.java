import java.math.BigDecimal;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Test2 {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  
  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "https://control-staging.mediaeverywhere.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testCreateCreative() throws Exception {
	  driver.get(baseUrl + "/");
	    driver.findElement(By.id("id_username")).sendKeys("corey.wu@polarmobile.com");
	    driver.findElement(By.id("id_password")).sendKeys("27131018699753UoW@");
	    driver.findElement(By.tagName("button")).click();
	    driver.findElement(By.linkText("Native Ad Creator")).click();
	    driver.findElements(By.className("btn")).get(0).click();
	    new Select(driver.findElement(By.id("id_extype-experience"))).selectByVisibleText("Article");
	    new Select(driver.findElement(By.id("id_extype-experience"))).selectByVisibleText("Outbound");
	    driver.findElement(By.cssSelector("option[value=\"outbound\"]")).click();
	    driver.findElement(By.id("id_creative-name")).sendKeys("Selenium Test1");
	    driver.findElement(By.id("id_creative-advertiser_name")).sendKeys("Selenium Test2");
	    driver.findElement(By.id("id_article-title")).sendKeys("Selenium Test3");
	    driver.findElement(By.id("id_article-description")).sendKeys("Selenium Test4");
	    driver.findElement(By.cssSelector("div.cta_actions > button.btn.btn-primary")).click();
	    driver.findElement(By.id("id_name")).clear();
	    driver.findElement(By.id("id_name")).sendKeys("Selenium Test5");
	    driver.findElement(By.id("id_text")).clear();
	    driver.findElement(By.id("id_text")).sendKeys("Selenium Test6");
	    driver.findElement(By.id("id_dest_url")).clear();
	    driver.findElement(By.id("id_dest_url")).sendKeys("https://www.google.ca/");
	    driver.findElement(By.cssSelector("button.btn.btn-primary")).click();
	    driver.findElement(By.cssSelector("input.btn.btn-success")).click();
	    //driver.findElement(By.cssSelector("div.cta_actions > button.btn.btn-primary")).click();
	    //driver.findElement(By.id("id_article-body_ifr")).sendKeys("Selenium Test5");
	    //assertEquals("Selenium Test5", driver.findElement(By.tagName("p")).getText());
	    //assertTrue(driver.findElements(By.linkText("Native Ad Creator")).size() > 0);
	 
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
