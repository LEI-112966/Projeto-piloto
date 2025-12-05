package TestSuite3.tests;

import com.codeborne.selenide.Selenide;
import TestSuite3.Film;
import TestSuite3.pages.FilmExampleDemoPage;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class ReadFilmsFromGridTest extends TestBase {
    @Test
    void readsFilmsFromGrid() {
        // create instance and initialize Selenide page fields
        FilmExampleDemoPage page = Selenide.page(FilmExampleDemoPage.class);

        List<Film> films = page.getFilms();
        assertFalse(films.isEmpty(), "Espera-se que a lista de filmes não esteja vazia");
    }
}
