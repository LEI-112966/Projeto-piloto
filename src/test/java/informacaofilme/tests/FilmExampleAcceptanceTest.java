package informacaofilme.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import informacaofilme.Film;
import informacaofilme.pages.FilmExampleDemoPage;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class FilmExampleAcceptanceTest {

    @BeforeAll
    static void globalSetup() {
        Configuration.headless = true;
        Configuration.browser = "chrome";
    }

    @BeforeEach
    void openApp() {
        Selenide.open("https://vaadin-database-example.demo.vaadin.com/");
    }

    @AfterEach
    void cleanup() {
        Selenide.closeWebDriver();
    }

    @Test
    void readsFilmsFromGrid() {
        FilmExampleDemoPage page = new FilmExampleDemoPage(WebDriverRunner.getWebDriver());
        List<Film> films = page.getFilms();
        assertFalse(films.isEmpty(), "Espera-se que a lista de filmes não esteja vazia");
    }

    @Test
    void clickImdbLinkNavigatesToImdb() {
        FilmExampleDemoPage page = new FilmExampleDemoPage(WebDriverRunner.getWebDriver());
        List<Film> films = page.getFilms();

        Film filmWithUrl = films.stream()
                .filter(f -> f.getImdbUrl() != null && !f.getImdbUrl().isEmpty())
                .findFirst()
                .orElse(null);

        assumeTrue(filmWithUrl != null, "Nehnum filme com URL IMDB disponível para teste");

        boolean clicked = page.clickImdbLinkByTitle(filmWithUrl.getTitle());
        assertTrue(clicked, "Espera-se que o link IMDB seja clicável");

        // Se o clique foi bem-sucedido, verifique a URL atual
        String current = WebDriverRunner.getWebDriver().getCurrentUrl();
        assertEquals(filmWithUrl.getImdbUrl(), current, "Navegador deve estar na URL IMDB correta");
    }

    @Test
    void sortByTitleOrdersFilmsAscending() {
        FilmExampleDemoPage page = new FilmExampleDemoPage(WebDriverRunner.getWebDriver());
        List<String> before = page.getFilms().stream()
                .map(Film::getTitle)
                .toList();

        // desired ascending order computed from initial snapshot
        List<String> desiredAsc = new ArrayList<>(before);
        desiredAsc.removeIf(String::isEmpty);
        desiredAsc.sort(String.CASE_INSENSITIVE_ORDER);

        boolean matched = false;
        // Click sorter up to two times to reach ascending order (toggle behavior)
        for (int i = 0; i < 2; i++) {
            page.sortByTitle();
            List<String> after = page.getFilms().stream()
                    .map(Film::getTitle)
                    .filter(s -> !s.isEmpty())
                    .toList();
            if (after.equals(desiredAsc)) {
                matched = true;
                break;
            }
        }

        assertTrue(matched, "A grid deve estar ordenada por título em ordem ascendente após a classificação");
    }
}
