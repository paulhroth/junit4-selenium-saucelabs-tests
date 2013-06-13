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
  public void testDelete() throws Exception {
    driver.get(baseUrl + "/");
    driver.findElement(By.id("id_username")).sendKeys("polarqa");
    driver.findElement(By.id("id_password")).sendKeys("4testing");
    driver.findElement(By.tagName("button")).click();
    driver.findElement(By.linkText("Native Ad Creator")).click();
    String link = driver.findElement(By.linkText("Selenium Test1")).getAttribute("href");
    String[] linkIds = link.split("/");
    int id = Integer.parseInt(linkIds[linkIds.length-1]);
    //assertEquals("https://control-staging.mediaeverywhere.com/native_ads/creative/38", driver.findElement(By.linkText("Selenium Test1")).getAttribute("href"));
    driver.findElement(By.xpath("//button[@onclick='$(\"#deletemodalcontainer\").load(\"/native_ads/creative/delete/" + id + "\");']")).click();
    assertEquals(" ", driver.findElements(By.tagName("button")).get(1).getAttribute("onclick"));
    synchronized (driver)
    {
        driver.wait(30000);
    }
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
