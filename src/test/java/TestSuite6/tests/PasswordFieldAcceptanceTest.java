package TestSuite6.tests;

import TestSuite6.pages.PasswordFieldPage;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordFieldAcceptanceTest extends TestBase {

    @Test
    void shouldSetAndReadPasswordValue() {
        PasswordFieldPage page = Selenide.page(PasswordFieldPage.class);
        page.openPasswordFieldExample();

        String testPassword = "Pa$$w0rd";
        page.setPasswordValue(testPassword);

        String actual = page.getPasswordValue();
        assertEquals(testPassword, actual, "O valor do password deve corresponder ao inserido.");
    }
}

