package TestSuite6.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

    @BeforeAll
    static void setupAll() {
        Configuration.baseUrl = "https://demo.vaadin.com/sampler/";

        // Definir um tamanho de browser válido. Se preferir maximizar, usar Configuration.startMaximized = true;
        Configuration.browserSize = "1920x1080";

        // Caso prefira iniciar maximizado ao invés de fixar o tamanho:
        // Configuration.startMaximized = true;

        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide().screenshots(true).savePageSource(false));
    }
}