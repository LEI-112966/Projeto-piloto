package testsuite8_basicfeatures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testsuite8_basicfeatures.pages.HomePage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BasicFeaturesTest extends TestBase {

    private HomePage homePage = new HomePage();

    @BeforeEach
    public void setupTest() {
        open("/"); // abre a página base do Vaadin Sampler
    }

    @Test
    public void testTooltipFeature() {
        homePage.openFeature("Tooltip");
        assertTrue(homePage.isFeatureVisible("Tooltip"), "Tooltip não está visível!");
    }

    @Test
    public void testIconFeature() {
        homePage.openFeature("Icon");
        assertTrue(homePage.isFeatureVisible("Icon"), "Icon não está visível!");
    }

    @Test
    public void testErrorsFeature() {
        homePage.openFeature("Errors");
        assertTrue(homePage.isFeatureVisible("Errors"), "Errors não está visível!");
    }

    @Test
    public void testTabIndexFeature() {
        homePage.openFeature("Tab index");
        assertTrue(homePage.isFeatureVisible("Tab index"), "Tab index não está visível!");
    }

    @Test
    public void testKeyboardShortcutsFeature() {
        homePage.openFeature("Keyboard Shortcuts");
        assertTrue(homePage.isFeatureVisible("Keyboard Shortcuts"), "Keyboard Shortcuts não está visível!");
    }
}
