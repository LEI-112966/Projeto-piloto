package iscteiul.ista.projetopiloto.pages.basic;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class InputsPage {

    private WebDriver driver;

    @FindBy(css = "input[type='number']")
    private WebElement numberInput;

    public InputsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void setInputValue(int value) {
        numberInput.clear();
        numberInput.sendKeys(String.valueOf(value));
    }

    public int getInputValue() {
        return Integer.parseInt(numberInput.getAttribute("value"));
    }
}
