package TestSuite4;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;

import java.nio.file.Paths;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

public class FormPage {

    private static final String URL = "https://vaadin-form-example.demo.vaadin.com/";

    public void openForm() {
        open(URL);
    }

    // --------- getters de elementos (lazy) ---------

    private SelenideElement firstNameField() {
        return inputInsideVaadinTextField(0);   // First name
    }

    private SelenideElement lastNameField() {
        return inputInsideVaadinTextField(1);   // Last name
    }

    private SelenideElement userHandleField() {
        return inputInsideVaadinTextField(2);   // User handle
    }

    private SelenideElement passwordField() {
        return inputInsideVaadinPasswordField(0);   // Wanted password
    }

    private SelenideElement passwordAgainField() {
        return inputInsideVaadinPasswordField(1);   // Password again
    }

    private SelenideElement marketingCheckbox() {
        SelenideElement host = $("vaadin-checkbox");
        SearchContext shadow = host.getWrappedElement().getShadowRoot();
        return $(shadow.findElement(By.cssSelector("[part='checkbox']")));
    }

    public void uploadAvatar(String filePath) {
        // host do vaadin-upload
        SelenideElement host = $("vaadin-upload");
        SearchContext shadow = host.getWrappedElement().getShadowRoot();

        // input[type=file] dentro do shadow-root
        SelenideElement fileInput = $(shadow.findElement(By.cssSelector("input[type='file']")));
        fileInput.uploadFile(Paths.get(filePath).toFile());
    }

    private SelenideElement joinButton() {
        SelenideElement host = $("vaadin-button[colspan='2'][theme='primary']");
        SearchContext shadow = host.getWrappedElement().getShadowRoot();
        return $(shadow.findElement(By.cssSelector("button#button")));
    }

    public void submit() {
        joinButton().shouldBe(enabled).click();
    }

    private SelenideElement successNotification() {
        return $("vaadin-notification-card");
    }


    // --------- ações de alto nível ---------

    public void fillFirstName(String firstName) {
        firstNameField().setValue(firstName);
    }

    public void fillLastName(String lastName) {
        lastNameField().setValue(lastName);
    }

    public void fillUserHandle(String handle) {
        userHandleField().setValue(handle);
    }

    public void fillPassword(String password) {
        passwordField().setValue(password);
    }

    public void fillPasswordAgain(String password) {
        passwordAgainField().setValue(password);
    }

    public void toggleMarketing() {
        marketingCheckbox().click();
    }

    public SelenideElement getSuccessNotification() {
        return successNotification();
    }

    // --------- helpers para shadow DOM ---------

    private SelenideElement inputInsideVaadinTextField(int index) {
        SelenideElement host = $$("vaadin-text-field").get(index);
        SearchContext shadow = host.getWrappedElement().getShadowRoot();
        return $(shadow.findElement(By.cssSelector("[part='input-field'] input")));
    }

    private SelenideElement inputInsideVaadinPasswordField(int index) {
        SelenideElement host = $$("vaadin-password-field").get(index);
        SearchContext shadow = host.getWrappedElement().getShadowRoot();
        return $(shadow.findElement(By.cssSelector("[part='input-field'] input")));
    }
}
