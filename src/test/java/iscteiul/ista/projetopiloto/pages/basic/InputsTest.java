package iscteiul.ista.projetopiloto.pages.basic;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputsTest {

    private WebDriver driver;

    @Test
    public void testInputs() {
        driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/inputs");

        InputsPage inputsPage = new InputsPage(driver);

        // Testa valores diferentes
        inputsPage.setInputValue(10);
        assertEquals(10, inputsPage.getInputValue());

        inputsPage.setInputValue(5);
        assertEquals(5, inputsPage.getInputValue());

        driver.quit();
    }
}
