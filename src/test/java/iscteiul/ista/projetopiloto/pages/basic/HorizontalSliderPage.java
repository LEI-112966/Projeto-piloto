package iscteiul.ista.projetopiloto.pages.basic;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HorizontalSliderPage {
    private WebDriver driver;

    @FindBy(css = "input[type='range']")
    private WebElement slider;

    public HorizontalSliderPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void moveSliderTo(double value) {
        // Primeiro clica no slider
        slider.click();
        // Move o slider usando as setas do teclado
        double current = Double.parseDouble(slider.getAttribute("value"));
        int steps = (int)((value - current) / 0.5); // cada passo = 0.5
        for (int i = 0; i < Math.abs(steps); i++) {
            if (steps > 0) slider.sendKeys(Keys.ARROW_RIGHT);
            else slider.sendKeys(Keys.ARROW_LEFT);
        }
    }

    public double getSliderValue() {
        return Double.parseDouble(slider.getAttribute("value"));
    }
}
