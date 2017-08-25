import model.Project;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import static junit.framework.TestCase.assertTrue;

public class TestSearchStatus {

    private WebDriver driver;
    private GetRandom getRandom=new GetRandom();

    public TestSearchStatus() throws IOException {
    }

    @Before
    public void setUp() throws Exception {
        String exePath = "driver/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", exePath);
    }

    @Test
    public void testSearchStatusValid() throws Exception {
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
        WebElement newScreen = driver.findElement(By.id("newScreen"));
        newScreen.click();
        Thread.sleep(2000);
        Project project=getRandom.getRandomProject();
        WebElement projectInput = driver.findElement(By.id("project"));
        projectInput.sendKeys(project.getCode());
        Thread.sleep(2000);
        List<WebElement> radioList = driver.findElements(By.name("radio"));
        radioButtonStatus(radioList);
        Thread.sleep(2000);
        WebElement searchButton=driver.findElement(By.id("search"));
        searchButton.click();
        Thread.sleep(2000);
        WebElement alert=driver.findElement(By.id("warningAlert"));
        assertTrue(!alert.isDisplayed());
    }
    @Test
    public void testSearchStatusNegativeEmptyProjectId() throws Exception {
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
        WebElement newScreen = driver.findElement(By.id("newScreen"));
        newScreen.click();
        Thread.sleep(2000);
        List<WebElement> radioList = driver.findElements(By.name("radio"));
        radioButtonStatus(radioList);
        Thread.sleep(2000);
        WebElement searchButton=driver.findElement(By.id("search"));
        searchButton.click();
        Thread.sleep(2000);
        WebElement alert=driver.findElement(By.id("warningAlert"));
        assertTrue(alert.isDisplayed());
    }
    @Test
    public void testSearchStatusNegativeInvalidProjectId() throws Exception {
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
        WebElement newScreen = driver.findElement(By.id("newScreen"));
        newScreen.click();
        Thread.sleep(2000);
        Project project=getRandom.getRandomProject();
        WebElement projectInput = driver.findElement(By.id("project"));
        projectInput.sendKeys(project.getCode().substring(0, ThreadLocalRandom.current().nextInt(project.getCode().length()-2, project.getCode().length()-1)));
        Thread.sleep(2000);
        List<WebElement> radioList = driver.findElements(By.name("radio"));
        radioButtonStatus(radioList);
        Thread.sleep(2000);
        WebElement searchButton=driver.findElement(By.id("search"));
        searchButton.click();
        Thread.sleep(2000);
        WebElement warning=driver.findElement(By.id("warningAlert"));
        assertTrue(warning.isDisplayed());
    }

    private static void radioButtonStatus(List<WebElement> radioList) throws InterruptedException {
        for (WebElement radio : radioList) {
            String radioValue = radio.getAttribute("value");
            if (radioValue.equalsIgnoreCase("status"))
                radio.click();
        }
    }
    @After
    public void tearDown() throws Exception {
        try {
            driver.close();

            driver.quit();
        }
        catch (Exception ignored){

        }
    }
   }
