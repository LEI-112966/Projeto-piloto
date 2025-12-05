package testsuite8_basicfeatures.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class HomePage {

    public void openFeature(String featureName) {
        SelenideElement titleElement = $$(".gwt-HTML.samplelink .title")
                .findBy(Condition.exactText(featureName))
                .shouldBe(Condition.visible, Duration.ofSeconds(10));

        titleElement.parent().click();
    }

    public boolean isFeatureVisible(String featureName) {
        try {
            switch (featureName) {
                case "Tooltip":
                    SelenideElement tooltipTarget = $$(".gwt-HTML.samplelink .title")
                            .findBy(Condition.exactText("Tooltip"))
                            .parent();

                    tooltipTarget.scrollTo().hover();

                    // aguarda até que o tooltip seja visível no DOM
                    return $$(".v-tooltip")
                            .findBy(Condition.visible)
                            .shouldBe(Condition.visible, Duration.ofSeconds(10))
                            .exists();

                case "Icon":
                    return $("vaadin-icon")
                            .shouldBe(Condition.visible, Duration.ofSeconds(10))
                            .exists();

                case "Errors":
                    return $("vaadin-text-field[error-message]")
                            .shouldBe(Condition.visible, Duration.ofSeconds(10))
                            .exists();

                case "Tab index":
                    return $("vaadin-button[tabindex]")
                            .shouldBe(Condition.visible, Duration.ofSeconds(10))
                            .exists();

                case "Keyboard Shortcuts":
                    return $("vaadin-shortcuts")
                            .shouldBe(Condition.visible, Duration.ofSeconds(10))
                            .exists();

                default:
                    throw new IllegalArgumentException("Feature não reconhecida: " + featureName);
            }
        } catch (Exception e) {
            System.err.println("Falha ao verificar a feature: " + featureName + " -> " + e.getMessage());
            return false;
        }
    }
}
