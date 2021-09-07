package avic;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class AvicSmokeTests {

    private WebDriver driver;

    @BeforeTest
    public void profileSetUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
    }

    @BeforeMethod
    public void testsSetUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://avic.ua/");
    }

    @Test(priority = 1)
    public void checkThatURLContainsSearchWord() {
        driver.findElement((By.xpath("//input[@id='input_search']"))).sendKeys("MacBook");
        driver.findElement((By.xpath("//button[@class='button-reset search-btn']"))).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        assertTrue(driver.getCurrentUrl().contains("query=MacBook"));
    }

    @Test(priority = 2)
    public void checkElementsAmountOnAppleStorePage() {
        driver.findElement(By.xpath("//span[@class='sidebar-item']")).click();
        driver.findElement(By.xpath("//ul[contains(@class,'sidebar-list')]//a[contains(@href, 'apple-store')]")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        List<WebElement> elements = driver.findElements(By.xpath("//div[@class='brand-box__title']"));
        assertEquals(elements.size(), 10);
    }

    @Test(priority = 3)
    public void checkElementsAmountOnIPhone12Page() {
        driver.findElement(By.xpath("//span[@class='sidebar-item']")).click();
        driver.findElement(By.xpath("//ul[contains(@class,'sidebar-list')]//a[contains(@href, 'apple-store')]")).click();
        driver.findElement(By.xpath("//div[@class='brand-box__title']/a[contains(@href,'iphone')]")).click();
        new WebDriverWait(driver, 30).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        List<WebElement> elements = driver.findElements(By.xpath("//div[@class='prod-cart__descr']"));
        assertEquals(elements.size(), 12);
    }

    @Test(priority = 4)
    public void checkSearchResultsContainsSearchWord() {
        driver.findElement(By.xpath("//input[@id='input_search']")).sendKeys("MacBook Air");
        driver.findElement(By.xpath("//button[@class='button-reset search-btn']")).click();
        List<WebElement> elements = driver.findElements(By.xpath("//div[@class='prod-cart__descr']"));
        for (WebElement element : elements) {
            assertTrue(element.getText().contains("MacBook Air"));
        }
    }

    @AfterMethod
    public void tearDown() {
        driver.close();
    }
}
