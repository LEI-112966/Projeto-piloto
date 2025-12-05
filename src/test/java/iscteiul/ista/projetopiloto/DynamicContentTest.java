package iscteiul.ista.projetopiloto;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DynamicContentTest {

    private WebDriver driver;
    private DynamicContentPage dynamicContentPage;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        dynamicContentPage = new DynamicContentPage(driver);
        dynamicContentPage.open();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void contentChangeAfterRefresh() {
        List<String> firstLoadTexts = dynamicContentPage.getRowTexts();
        dynamicContentPage.refresh();
        List<String> secondLoadTexts = dynamicContentPage.getRowTexts();
        assertNotEquals(firstLoadTexts, secondLoadTexts);
    }

    @Test
    public void hasAtLeastThreeRows() {
        List<String> texts = dynamicContentPage.getRowTexts();
        assertTrue(texts.size() >= 3);
    }

    @Test
    public void rowsNotBeEmpty() {
        List<String> texts = dynamicContentPage.getRowTexts();
        assertTrue(texts.stream().anyMatch(t -> !t.trim().isEmpty()));
    }

}
