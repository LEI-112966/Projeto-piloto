package iscteiul.ista.projetopiloto;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

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
            WebElement acceptCookieButton = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//*[contains(text(), 'Accept All')]")
                    )
            );
            acceptCookieButton.click();
            System.out.println("Janela de cookies fechada com sucesso.");
            Thread.sleep(1000);

        } catch (Exception e) {
            System.out.println("A janela de cookies 'Accept All' não foi encontrada ou já estava fechada.");
        }
        try {
            WebElement closeBanner = driver.findElement(By.xpath("//button[@aria-label='Close' or contains(@class, 'close')]"));
            if(closeBanner.isDisplayed()) {
                closeBanner.click();
                System.out.println("Banner de topo fechado.");
            }
        } catch (Exception e) {
        }

        mainPage = new MainPage(driver);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void search(){
        String searchKeyword = "Selenium";
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        mainPage.searchButton.click();
        WebElement searchField = wait.until(
                ExpectedConditions.elementToBeClickable(By.cssSelector("input[data-test-id='search-input']"))
        );

        searchField.sendKeys(searchKeyword);
        searchField.sendKeys(Keys.ENTER);

        try { Thread.sleep(5000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        WebElement searchPageField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[data-test-id='search-input']"))
        );

        WebElement searchField2 = wait.until(
                ExpectedConditions.elementToBeClickable(By.cssSelector("input[data-test-id='search-input']"))
        );

        searchField2.sendKeys(searchKeyword);
        searchField2.sendKeys(Keys.ENTER);

        WebElement searchPageField2 = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[data-test-id='search-input']"))
        );


        try { Thread.sleep(2000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        assertEquals(searchKeyword, searchPageField2.getAttribute("value"));
    }

    @Test
    public void toolsMenu() {
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
