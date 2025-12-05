package iscteiul.ista.projetopiloto.pages.dynamic;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

public class DynamicContentPage {

    private WebDriver driver;
    private static final String URL = "https://the-internet.herokuapp.com/dynamic_content";

    @FindBy(css = "#content .row")
    private List<WebElement> rows;

    public DynamicContentPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get(URL);
    }

    public List<String> getRowTexts() {
        return rows.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public void refresh() {
        driver.navigate().refresh();
    }
}
