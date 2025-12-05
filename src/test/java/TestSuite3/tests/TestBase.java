package TestSuite3.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class TestBase {
    @BeforeAll
    static void setupAll() {
        Configuration.browser = "chrome";
        Configuration.baseUrl = "https://vaadin-database-example.demo.vaadin.com/";
        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide().screenshots(true).savePageSource(false));
    }
    @BeforeEach
    void setupEach() {
        // Before each test, ensure we are on the base URL
        Selenide.open("/");
    }
}
