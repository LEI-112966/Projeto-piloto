package iscteiul.ista.projetopiloto;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class MainPageTest {

    private WebDriver driver;
    private MainPage mainPage;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.jetbrains.com/");

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement acceptCookies = wait.until(
                    expectedCondition -> expectedCondition.findElement(
                            By.xpath("//*[contains(text(), 'Accept All')]")
                    )
            );
            acceptCookies.click();
        } catch (Exception e) {
            // If the cookie consent button is not found, proceed without throwing an error
        }

        mainPage = new MainPage(driver);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void search() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        mainPage.searchButton.click();
        WebElement searchField = wait.until(
                ExpectedConditions.elementToBeClickable(By.cssSelector("input[data-test-id='search-input']"))
        );

        searchField.sendKeys("Selenium");

        WebElement searchPageField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[data-test-id='search-input']"))
        );
        assertEquals("Selenium", searchPageField.getAttribute("value"));
    }


    @Test
    public void toolsMenu() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Abre o menu (Developer Tools / menu principal)
        mainPage.toolsMenu.click();

        WebElement menuPopup = driver.findElement(By.cssSelector("div[data-test='main-menu-item']"));
        assertTrue(menuPopup.isDisplayed());
    }



    @Test
    public void navigationToAllTools() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(mainPage.seeDeveloperToolsButton));
        mainPage.seeDeveloperToolsButton.click();
        wait.until(ExpectedConditions.elementToBeClickable(mainPage.findYourToolsButton));
        new org.openqa.selenium.interactions.Actions(driver) //cria uma nova ação do Selenium
                .moveToElement(mainPage.findYourToolsButton) //move o cursor até ao botão
                .pause(java.time.Duration.ofMillis(150)) //pausa para simular o tempo de reação do utilizador
                .click() //clica no botão
                .perform(); //executa a ação

        WebElement productsList = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("products-page"))
        );
        assertTrue(productsList.isDisplayed());
        wait.until(ExpectedConditions.titleIs("All Developer Tools and Products by JetBrains"));
        assertEquals("All Developer Tools and Products by JetBrains", driver.getTitle());
    }
}