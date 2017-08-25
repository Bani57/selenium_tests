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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class TestPriorityTable {

    private WebDriver driver;
    private GetRandom getRandom=new GetRandom();
    public TestPriorityTable() throws IOException {
    }

    @Before
    public void setUp() throws Exception {
        String exePath = "driver/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", exePath);

    }
    private void clickRadio(List<WebElement> radioList) throws Exception
    {
        for (WebElement radio : radioList) {
            String radioValue = radio.getAttribute("value");
            if (radioValue.equalsIgnoreCase("Priority")) {
                Thread.sleep(2000);
                radio.click();
            }
        }
    }
    @Test
    public void testPriorityValidTable () throws Exception {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-fullscreen");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9000");
        Thread.sleep(2000);
        User user=getRandom.getRandomUser();
        WebElement username=driver.findElement(By.id("username"));
        username.sendKeys(user.getUsername());
        Thread.sleep(2000);
        WebElement password = driver.findElement(By.id("pwd"));
        password.sendKeys(user.getPassword());
        Thread.sleep(2000);
        WebElement button = driver.findElement(By.className("btn"));
        button.click();
        Thread.sleep(2000);
        String url = driver.getCurrentUrl();
        assertTrue(url.equals("http://localhost:9000/#!/main/home"));
        Thread.sleep(2000);
        WebElement newScreen = driver.findElement(By.id("newScreen"));
        newScreen.click();
        Thread.sleep(2000);
        Project project=getRandom.getRandomProject();
        WebElement projectInput = driver.findElement(By.id("project"));
        projectInput.sendKeys(project.getCode());
        Thread.sleep(2000);
        List<WebElement> radioList = driver.findElements(By.name("radio"));
        clickRadio(radioList);
        Thread.sleep(2000);
        WebElement srcbtn = driver.findElement(By.id("search"));
        srcbtn.click();
        Thread.sleep(2000);
        WebElement table = driver.findElement(By.id("issueTable"));
        List<WebElement> rows=table.findElements(By.xpath("id('issueTable')/tbody/tr"));
        int rownum=rows.size();
        assertTrue(rownum==7);
        List<String> priorities = new ArrayList<>();
        priorities.add("LOW");
        priorities.add("MEDIUM");
        priorities.add("HIGH");
        priorities.add("CRITICAL");
        priorities.add("BLOCKER");
        priorities.add("MAJOR");
        priorities.add("MINOR");
        int counter=0;
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.xpath("td"));
            assertEquals(cells.get(0).getText(), priorities.get(counter));
            counter++;
        }
    }
    @Test
    public void testPriorityInvalidTable () throws Exception {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-fullscreen");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9000");
        Thread.sleep(2000);
        User user=getRandom.getRandomUser();
        WebElement username=driver.findElement(By.id("username"));
        username.sendKeys(user.getUsername());
        Thread.sleep(2000);
        WebElement password = driver.findElement(By.id("pwd"));
        password.sendKeys(user.getPassword());
        Thread.sleep(2000);
        WebElement button = driver.findElement(By.className("btn"));
        button.click();
        Thread.sleep(2000);
        String url = driver.getCurrentUrl();
        assertTrue(url.equals("http://localhost:9000/#!/main/home"));
        Thread.sleep(2000);
        WebElement newScreen = driver.findElement(By.id("newScreen"));
        newScreen.click();
        Thread.sleep(2000);
        Project project=getRandom.getRandomProject();
        WebElement projectInput = driver.findElement(By.id("project"));
        projectInput.sendKeys(project.getCode().substring(0, ThreadLocalRandom.current().nextInt(project.getCode().length()-2, project.getCode().length()-1)));
        Thread.sleep(2000);
        List<WebElement> radioList = driver.findElements(By.name("radio"));
        clickRadio(radioList);
        Thread.sleep(2000);
        WebElement srcbtn = driver.findElement(By.id("search"));
        srcbtn.click();
        Thread.sleep(2000);
        WebElement table = driver.findElement(By.id("issueTable"));
        List<WebElement> rows=table.findElements(By.xpath("id('issueTable')/tbody/tr"));
        int rownum=rows.size();
        assertTrue(rownum==0);
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

