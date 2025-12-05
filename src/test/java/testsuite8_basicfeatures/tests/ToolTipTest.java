package testsuite8_basicfeatures.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testsuite8_basicfeatures.pages.ToolTipPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ToolTipTest extends TestBase {

    private ToolTipPage tooltipPage;

    @BeforeEach
    public void setupTest() {
        tooltipPage = new ToolTipPage();
        tooltipPage.openPage(); // chama o método correto
    }

    @Test
    public void testPlainTooltipIsVisible() {
        assertTrue(tooltipPage.isPlainTooltipVisible(), "O tooltip 'plain' não está visível ao hover!");
    }
}
