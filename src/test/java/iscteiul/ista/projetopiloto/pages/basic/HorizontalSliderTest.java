package iscteiul.ista.projetopiloto.pages.basic;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HorizontalSliderTest {

    private WebDriver driver;

    @Test
    public void testHorizontalSlider() {
        driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/horizontal_slider");

        HorizontalSliderPage sliderPage = new HorizontalSliderPage(driver);
        sliderPage.moveSliderTo(5);
        assertEquals(5.0, sliderPage.getSliderValue(), 0.01);

        driver.quit();
    }
}
