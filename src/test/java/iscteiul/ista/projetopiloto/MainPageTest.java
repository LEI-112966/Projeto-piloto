package iscteiul.ista.projetopiloto;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
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

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Fechar popup de cookies se aparecer
        try {
            WebElement acceptCookiesButton = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.cssSelector("button.ch2-allow-all-btn")
                    )
            );
            acceptCookiesButton.click();
        } catch (Exception ignored) {
            // popup não apareceu, continua
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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        String searchKeyword = "Selenium";

        // Abre a caixa de pesquisa
        mainPage.searchButton.click();

        // Campo de pesquisa
        WebElement searchField = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("input[data-test-id='search-input']")
                )
        );
        searchField.sendKeys(searchKeyword);
        searchField.sendKeys(Keys.ENTER);

        // Espera por um elemento típico da página de resultados
        WebElement announcePlaceholder = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("div#announce-placeholder[data-product-name='default']")
                )
        );

        assertTrue(announcePlaceholder.isDisplayed());
    }


    @Test
    public void toolsMenu() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Abre o menu (Developer Tools / menu principal)
        mainPage.toolsMenu.click();

        // Espera pelo link "All IDEs" no submenu
        WebElement allIdesLink = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("a[data-test='main-submenu-item-link'][href='/ides/']")
                )
        );
        allIdesLink.click();

        // Verifica que a página de IDEs carregou (ajusta o seletor conforme o HTML dessa página)
        wait.until(ExpectedConditions.urlContains("/ides/"));
        assertTrue(driver.getCurrentUrl().contains("/ides/"));
    }



    @Test
    public void navigationToAllTools() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.elementToBeClickable(mainPage.seeDeveloperToolsButton));
        mainPage.seeDeveloperToolsButton.click();

        wait.until(ExpectedConditions.elementToBeClickable(mainPage.findYourToolsButton));

        new Actions(driver)
                .moveToElement(mainPage.findYourToolsButton)
                .pause(Duration.ofMillis(150))
                .click()
                .perform();

        WebElement productsList = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("products-page")) // ajustar se necessário
        );
        assertTrue(productsList.isDisplayed());

        wait.until(ExpectedConditions.titleIs("All Developer Tools and Products by JetBrains"));
        assertEquals("All Developer Tools and Products by JetBrains", driver.getTitle());
    }
}

