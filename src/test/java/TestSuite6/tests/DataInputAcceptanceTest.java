package TestSuite6.tests;

import TestSuite6.pages.DataInputPage;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DataInputAcceptanceTest extends TestBase {

    @Test
    void shouldAccessDataInputAndTypeValue() {
        // Inicializa o Page Object usando PageFactory internamente (Selenide.page)
        DataInputPage page = Selenide.page(DataInputPage.class);

        // Abre a página do exemplo Text Field
        page.openTextFieldExample();

        String testValue = "Teste 123";
        page.setFirstInputValue(testValue);

        String actual = page.getFirstInputValue();
        assertEquals(testValue, actual, "O valor no campo deve corresponder ao inserido.");
    }
}
