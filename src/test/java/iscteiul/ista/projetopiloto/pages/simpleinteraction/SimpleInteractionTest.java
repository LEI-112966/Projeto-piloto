package iscteiul.ista.projetopiloto.pages.simpleinteraction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleInteractionTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    public void testCheckboxes() {
        driver.get("https://the-internet.herokuapp.com/checkboxes");

        // locate the checkbox inputs directly
        List<WebElement> checkboxes = driver.findElements(By.cssSelector("#checkboxes input[type='checkbox']"));
        wait.until(ExpectedConditions.visibilityOf(checkboxes.get(0)));

        // initial expected state: first unchecked, second checked
        assertFalse(checkboxes.get(0).isSelected(), "A primeira checkbox deve estar desmarcada por defeito");
        assertTrue(checkboxes.get(1).isSelected(), "A segunda checkbox deve estar marcada por defeito");

        // toggle first checkbox (check it) and verify
        checkboxes.get(0).click();
        assertTrue(checkboxes.get(0).isSelected(), "A primeira checkbox deve estar marcada após o clique");

        // toggle second checkbox (uncheck it) and verify
        checkboxes.get(1).click();
        assertFalse(checkboxes.get(1).isSelected(), "A segunda checkbox deve estar desmarcada após o clique");
    }

    @Test
    public void testDropdown() {
        driver.get("https://the-internet.herokuapp.com/dropdown");

        WebElement dropdownElement = driver.findElement(By.id("dropdown"));
        wait.until(ExpectedConditions.visibilityOf(dropdownElement));

        Select dropdown = new Select(dropdownElement);

        // default option text assertion
        String defaultText = dropdown.getFirstSelectedOption().getText().trim();
        assertEquals("Please select an option", defaultText, "Dropdown default deve ser um prompt");

        // select Option 1 and verify
        dropdown.selectByVisibleText("Option 1");
        assertEquals("Option 1", dropdown.getFirstSelectedOption().getText().trim(), "Dropdown deve ter a Option 1 selecionada");

        // select Option 2 and verify
        dropdown.selectByVisibleText("Option 2");
        assertEquals("Option 2", dropdown.getFirstSelectedOption().getText().trim(), "Dropdown deve ter a Option 2 selecionada");
    }
}
