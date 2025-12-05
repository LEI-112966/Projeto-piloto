package testsuite8_basicfeatures.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ToolTipPage {

    private static final String URL = "/#ui/basic-features/tooltip";

    /** Método de instância para abrir a página do Tooltip */
    public void openPage() {
        open(URL);  // usa o Selenide.open
    }

    /** Retorna o elemento alvo do tooltip "plain" */
    public SelenideElement getPlainTooltipTarget() {
        return $(".v-label.v-widget.v-label-undef-w")
                .shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    /** Verifica se o tooltip aparece ao passar o mouse */
    public boolean isPlainTooltipVisible() {
        getPlainTooltipTarget().hover();
        return $(".v-tooltip")
                .shouldBe(Condition.visible, Duration.ofSeconds(5))
                .exists();
    }
}
