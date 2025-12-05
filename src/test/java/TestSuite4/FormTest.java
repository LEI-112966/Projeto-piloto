package TestSuite4;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.closeWebDriver;

@TestMethodOrder(MethodOrderer.DisplayName.class)
public class FormTest {

    private FormPage formPage;

    @BeforeAll
    static void setupSelenide() {
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 10000;
    }

    @BeforeEach
    void setup() {
        formPage = new FormPage();
        openFormPage();  // chama formPage.openForm()
    }

    @Step("Abrir a página do formulário")
    void openFormPage() {
        formPage.openForm();
    }

    @Test
    @DisplayName("4.1 - Fazer parte da comunidade com dados válidos")
    @Description("Preenche o formulário de adesão à comunidade e verifica que a submissão é bem sucedida.")
    void shouldSubmitCommunityFormSuccessfully() {
        fillFormWithValidData();
        uploadAvatarImage();
        //acceptMarketing();
        submitForm();
        //verifySuccessfulSubmission();
    }

    @Step("Fazer upload de uma imagem de avatar")
    void uploadAvatarImage() {
        formPage.uploadAvatar("src/test/resources/cp.png");
    }

    @Step("Preencher o formulário com dados válidos")
    void fillFormWithValidData() {
        formPage.fillFirstName("Gui");
        formPage.fillLastName("Sil");
        formPage.fillUserHandle("gui" + System.currentTimeMillis());
        formPage.fillPassword("SecurePass123!");
        formPage.fillPasswordAgain("SecurePass123!");
    }

    @Step("Aceitar receber marketing")
    void acceptMarketing() {
        formPage.toggleMarketing();
    }

    @Step("Submeter o formulário")
    void submitForm() {
        formPage.submit();
    }

    @Step("Verificar submissão com sucesso")
    void verifySuccessfulSubmission() {
        formPage.getSuccessNotification()
                .shouldBe(visible);
        // .shouldHave(text("..."))  -> se quiseres validar o texto
    }

    @AfterEach
    void tearDown() {
        closeWebDriver();
    }
}
