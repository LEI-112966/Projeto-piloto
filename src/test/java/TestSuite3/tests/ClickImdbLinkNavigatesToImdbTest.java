package TestSuite3.tests;

import com.codeborne.selenide.Selenide;
import TestSuite3.Film;
import TestSuite3.pages.FilmExampleDemoPage;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class ClickImdbLinkNavigatesToImdbTest extends TestBase {
    @Test
    void clickImdbLinkNavigatesToImdb() {
        FilmExampleDemoPage page = Selenide.page(FilmExampleDemoPage.class);
        List<Film> films = page.getFilms();

        Film filmWithUrl = films.stream()
                .filter(f -> f.getImdbUrl() != null && !f.getImdbUrl().isEmpty())
                .findFirst()
                .orElse(null);

        assumeTrue(filmWithUrl != null, "Nehnum filme com URL IMDB disponível para teste");

        boolean clicked = page.clickImdbLinkByTitle(filmWithUrl.getTitle());
        assertTrue(clicked, "Espera-se que o link IMDB seja clicável");

        // Se o clique foi bem-sucedido, verifique a URL atual
        String current = com.codeborne.selenide.WebDriverRunner.getWebDriver().getCurrentUrl();
        assertEquals(filmWithUrl.getImdbUrl(), current, "Navegador deve estar na URL IMDB correta");
    }
}
