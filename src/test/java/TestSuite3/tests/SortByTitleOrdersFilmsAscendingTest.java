package TestSuite3.tests;

import com.codeborne.selenide.Selenide;
import TestSuite3.Film;
import TestSuite3.pages.FilmExampleDemoPage;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SortByTitleOrdersFilmsAscendingTest extends TestBase {
    @Test
    void sortByTitleOrdersFilmsAscending() {
        FilmExampleDemoPage page = Selenide.page(FilmExampleDemoPage.class);
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
