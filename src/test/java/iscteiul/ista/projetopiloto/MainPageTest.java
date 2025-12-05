package iscteiul.ista.projetopiloto;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

        mainPage = new MainPage(driver);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void search() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Fecha banner de cookies se aparecer
        try {
            WebElement acceptCookies = driver.findElement(By.cssSelector("div.ch2-container button"));
            if (acceptCookies.isDisplayed()) {
                acceptCookies.click();
            }
        } catch (Exception e) {
            // Banner não apareceu
        }

        // Clica no ícone de pesquisa
        wait.until(ExpectedConditions.elementToBeClickable(mainPage.searchButton)).click();

        // Espera até que o input da pesquisa esteja presente no DOM e visível
        By searchInputLocator = By.cssSelector("input[data-test-id='search-input']");
        WebElement searchField = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInputLocator));

        // Digita a pesquisa
        searchField.sendKeys("Selenium");

        // Submete a pesquisa
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[data-test='full-search-button']")));
        submitButton.click();

        // Validação do valor
        WebElement searchPageField = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInputLocator));
        assertEquals("Selenium", searchPageField.getAttribute("value"));
    }

    @Test
    public void toolsMenu() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Fecha banner de cookies se existir
        try {
            WebElement acceptCookies = driver.findElement(By.cssSelector("div.ch2-container button"));
            if (acceptCookies.isDisplayed()) {
                acceptCookies.click();
            }
        } catch (Exception ignored) {}

        // Clica no menu "Tools"
        wait.until(ExpectedConditions.elementToBeClickable(mainPage.toolsMenu)).click();

        // Localizador do submenu
        By submenuLocator = By.cssSelector("div[data-test='main-submenu']");

        // Espera o submenu estar presente no DOM
        wait.until(ExpectedConditions.presenceOfElementLocated(submenuLocator));

        // Pega o submenu
        WebElement menuPopup = driver.findElement(submenuLocator);

        // Verifica se contém pelo menos um item (ou texto)
        assertTrue(menuPopup.findElements(By.cssSelector("a")).size() > 0, "Submenu deveria conter links");
    }

    @Test
    public void navigationToAllTools() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Fecha banner de cookies se existir
        try {
            WebElement acceptCookies = driver.findElement(By.cssSelector("div.ch2-container button"));
            if (acceptCookies.isDisplayed()) {
                acceptCookies.click();
            }
        } catch (Exception ignored) {}

        // Abre o menu Developer Tools
        mainPage.seeDeveloperToolsButton.click();

        // Espera diretamente pelo link clicável
        WebElement findYourToolLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("[data-test='suggestion-link']")
        ));
        findYourToolLink.click();

        // Verifica que está na página
        WebElement productsList = driver.findElement(By.id("products-page"));
        assertTrue(productsList.isDisplayed());
        assertEquals("All Developer Tools and Products by JetBrains", driver.getTitle());
    }
}
