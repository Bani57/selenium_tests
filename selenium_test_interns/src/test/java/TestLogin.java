import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import static junit.framework.TestCase.assertTrue;
public class TestLogin {

    private WebDriver driver;
    private GetRandom getRandom=new GetRandom();

    public TestLogin() throws IOException {
    }

    @Before
    public void setUp() throws Exception {
        String exePath = "driver/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", exePath);
    }

    @Test
    public void testLogin() throws Exception {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-fullscreen");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9000");
        Thread.sleep(2000);
        User user=getRandom.getRandomUser();
        WebElement username=driver.findElement(By.id("username"));
        username.sendKeys(user.getUsername());
        Thread.sleep(2000);
        WebElement password=driver.findElement(By.id("pwd"));
        password.sendKeys(user.getPassword());
        Thread.sleep(2000);
        WebElement button=driver.findElement(By.className("btn"));
        button.click();
        Thread.sleep(2000);
        String url=driver.getCurrentUrl();
        assertTrue(url.equals("http://localhost:9000/#!/main/home"));
        Thread.sleep(2000);
        WebElement logout=driver.findElement(By.id("logout"));
        logout.click();
        Thread.sleep(2000);
        url=driver.getCurrentUrl();
        assertTrue(url.equals("http://localhost:9000/#!/main"));
        WebElement success=driver.findElement(By.id("logoutAlert"));
        assertTrue(success.isDisplayed());
    }

    @Test
    public void testLoginNegativeWrongUsername() throws Exception {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-fullscreen");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9000");
        Thread.sleep(2000);
        User user=getRandom.getRandomUser();
        WebElement username=driver.findElement(By.id("username"));
        username.sendKeys(user.getUsername().substring(0, ThreadLocalRandom.current().nextInt(user.getUsername().length()-3, user.getUsername().length()-1)));
        Thread.sleep(2000);
        WebElement password=driver.findElement(By.id("pwd"));
        password.sendKeys(user.getPassword());
        Thread.sleep(2000);
        WebElement button=driver.findElement(By.className("btn"));
        button.click();
        Thread.sleep(2000);
        WebElement alert=driver.findElement(By.id("warningAlert"));
        assertTrue(alert.isDisplayed());
    }
    @Test
    public void testLoginNegativeWrongPassword() throws Exception {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-fullscreen");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9000");
        Thread.sleep(2000);
        User user=getRandom.getRandomUser();
        WebElement username=driver.findElement(By.id("username"));
        username.sendKeys(user.getUsername());
        Thread.sleep(2000);
        WebElement password=driver.findElement(By.id("pwd"));
        password.sendKeys(user.getPassword().substring(0,ThreadLocalRandom.current().nextInt(user.getPassword().length()-2, user.getPassword().length()-1)));
        Thread.sleep(2000);
        WebElement button=driver.findElement(By.className("btn"));
        button.click();
        Thread.sleep(2000);
        WebElement alert=driver.findElement(By.id("warningAlert"));
        assertTrue(alert.isDisplayed());
    }
    @Test
    public void testLoginNegativeEmpty() throws Exception {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-fullscreen");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9000");
        Thread.sleep(2000);
        WebElement button=driver.findElement(By.className("btn"));
        button.click();
        Thread.sleep(2000);
        WebElement alert=driver.findElement(By.id("warningAlert"));
        assertTrue(alert.isDisplayed());
    }

    @Test
    public void testRedirect() throws Exception {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-fullscreen");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9000");
        Thread.sleep(2000);
        driver.get("http://localhost:9000/#!/main/home");
        Thread.sleep(2000);
        WebDriverWait wait=new WebDriverWait(driver,3);
        wait.until(ExpectedConditions.alertIsPresent());
        assertTrue(driver.switchTo().alert().getText().equals("No session valid! Redirecting to Login Page"));
    }

    @After
    public void tearDown() throws Exception {
        try {
            driver.close();
            driver.quit();
        }
        catch (Exception ignored)
        {
        }
    }
}
