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

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class TestTableStatus {

    private WebDriver driver;
    private GetRandom getRandom=new GetRandom();

    public TestTableStatus() throws IOException {
    }

    @Before
    public void setUp() throws Exception {
        String exePath = "driver/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", exePath);
    }

    private void clickRadio(List<WebElement> radioList) throws InterruptedException {
        for (WebElement radio : radioList) {
            String radioValue = radio.getAttribute("value");
            if (radioValue.equalsIgnoreCase("Status"))
                radio.click();
        }
    }


    @Test
    public void testStatusTable() throws Exception {
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
        List<String> podatoci = new ArrayList<>();
        podatoci.add("OPEN");
        podatoci.add("IN PROGRESS");
        podatoci.add("CLOSED");
        podatoci.add("IN TESTING");
        podatoci.add("IN PLANNING");
        WebElement table_ele = driver.findElement((By.id("issueTable")));
        List<WebElement> table_tr = table_ele.findElements(By.xpath("id('issueTable')/tbody/tr"));
        assertTrue(table_tr.size()==5);
        int row_num = 0;
        for (WebElement trElement : table_tr){
            List<WebElement> td_collection = trElement.findElements(By.xpath("td"));
            assertEquals(td_collection.get(0).getText(),podatoci.get(row_num));
            row_num++;
        }
    }

    @Test
    public void testStatusTableInvalidProjectCode() throws Exception {
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
        WebElement table_ele = driver.findElement((By.id("issueTable")));
        List<WebElement> table_tr = table_ele.findElements(By.xpath("id('issueTable')/tbody/tr"));
        assertTrue(table_tr.size()==0);
    }

    @After
    public void tearDown() throws Exception {
        try{
            driver.close();
            driver.quit();
        }
        catch(Exception ignored)
        {

        }
    }
}
