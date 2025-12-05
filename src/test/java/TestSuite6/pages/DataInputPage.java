package TestSuite6.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.Selenide;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.open;

public class DataInputPage {

    private final String baseUrl = "https://demo.vaadin.com/sampler/";

    @FindBy(css = "vaadin-text-field input, vaadin-password-field input, textarea, input[type='text'], input[type='date'], input[type='password']")
    private ElementsCollection inputs;

    public DataInputPage() {
        // Inicializa os elementos anotados (@FindBy) para esta instância
        Selenide.page(this);
    }

    public DataInputPage openComponentByFragment(String fragment) {
        open(baseUrl + fragment);
        // Re-inicializa para ligar os elementos ao novo DOM carregado
        Selenide.page(this);
        return this;
    }

    // Localiza inputs com Selenide (PageFactory)
    public ElementsCollection allInputs() {
        return inputs;
    }

    public SelenideElement firstVisibleInput() {
        return inputs.filter(visible).first();
    }

    public void setFirstInputValue(String value) {
        SelenideElement input = firstVisibleInput();
        if (input == null) throw new IllegalStateException("Nenhum input visível encontrado.");
        input.clear();
        input.setValue(value);
    }

    public String getFirstInputValue() {
        SelenideElement input = firstVisibleInput();
        return input == null ? null : input.getValue();
    }

    public DataInputPage openTextFieldExample() {
        return openComponentByFragment("#ui/data-input/text-input/text-field");
    }
}
