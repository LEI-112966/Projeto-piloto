package TestSuite6.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.Selenide;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.open;

public class PasswordFieldPage {

    private final String baseUrl = "https://demo.vaadin.com/sampler/";

    @FindBy(css = "vaadin-password-field input, input[type='password']")
    private ElementsCollection inputs;

    public PasswordFieldPage() {
        Selenide.page(this);
    }

    public PasswordFieldPage openComponentByFragment(String fragment) {
        open(baseUrl + fragment);
        Selenide.page(this);
        return this;
    }

    public PasswordFieldPage openPasswordFieldExample() {
        // Assunção: fragment plausível para o exemplo de password field
        return openComponentByFragment("#ui/data-input/text-input/password-field");
    }

    public SelenideElement firstVisibleInput() {
        return inputs.filter(visible).first();
    }

    public void setPasswordValue(String password) {
        SelenideElement input = firstVisibleInput();
        if (input == null) throw new IllegalStateException("Nenhum campo de password visível encontrado.");
        input.clear();
        input.setValue(password);
    }

    public String getPasswordValue() {
        SelenideElement input = firstVisibleInput();
        return input == null ? null : input.getValue();
    }
}

