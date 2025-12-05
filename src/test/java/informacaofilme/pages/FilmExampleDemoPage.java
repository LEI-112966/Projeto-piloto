package informacaofilme.pages;
import informacaofilme.Film;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

/**
 * Page object for the film example demo page.
 * Assumes the grid has 4 columns in order: Title, Release Year, Director, IMDB Link.
 * Converted to use Selenide Page Factory (@FindBy + Selenide.page(this)).
 */
public class FilmExampleDemoPage {
    @FindBy(css = "vaadin-grid")
    private SelenideElement grid;

    @FindBy(css = "vaadin-grid-sorter[path='col5']")
    private SelenideElement sorterTitle; // header sorter for Title

    public FilmExampleDemoPage() {
        // no-op: fields are initialized by Selenide.page(Class)
    }

    // small helper to avoid potential NPEs when checking emptiness
    private boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    /**
     * Reads the visible grid cells and builds a list of Film objects.
     * This implementation groups cell contents in batches of 4 (title, year, director, link).
     */
    public List<Film> getFilms() {
        // ensure the grid is visible before interacting
        grid.shouldBe(Condition.visible);
        ElementsCollection cellElements = grid.$$(By.cssSelector("vaadin-grid-cell-content"));

        List<Film> films = new ArrayList<>();
        int cols = 4;
        // Defensive: if the header cells are present among cell elements, skip them by filtering anchors/text content heuristics.
        List<String> texts = new ArrayList<>();
        for (SelenideElement c : cellElements) {
            String text = c.getText().trim();
            SelenideElement link = null;
            try {
                link = c.$("a");
            } catch (Exception ignored) {}
            if (link != null && link.exists() && (text.isEmpty() || text.startsWith("Click"))) {
                texts.add("::LINK::" + (link.getAttribute("href") != null ? link.getAttribute("href") : ""));
            } else {
                texts.add(text);
            }
        }

        // Try to find first real data row start by locating a title-like entry
        // If number of texts is not divisible by cols, attempt to drop header chunk at beginning.
        int startIndex = 0;
        if (texts.size() % cols != 0) {
            // find first index where remaining count % cols == 0 and text looks like a title (non-empty)
            for (int i = 0; i < texts.size(); i++) {
                if (!texts.get(i).isEmpty() && (texts.size() - i) % cols == 0) {
                    startIndex = i;
                    break;
                }
            }
        }

        for (int i = startIndex; i + cols - 1 < texts.size(); i += cols) {
            String title = texts.get(i);
            String year = texts.get(i + 1);
            String director = texts.get(i + 2);
            String linkToken = texts.get(i + 3);
            String href = "";
            if (linkToken.startsWith("::LINK::")) {
                href = linkToken.replaceFirst("::LINK::", "");
            } else {
                // try to fetch anchor from that particular cell element directly
                try {
                    SelenideElement cellElement = cellElements.get(i + 3);
                    SelenideElement a = cellElement.$("a");
                    if (a.exists()) href = a.getAttribute("href");
                } catch (Exception ignored) {}
            }
            if (isNullOrEmpty(title) && isNullOrEmpty(year) && isNullOrEmpty(director) && isNullOrEmpty(href)) {
                continue; // skip empty rows
            }
            films.add(new Film(title, year, director, href));
        }
        return films;
    }

    /**
     * Clicks the IMDB link for the first film matching the title.
     * Returns true if clicked, false if not found.
     */
    public boolean clickImdbLinkByTitle(String filmTitle) {
        grid.shouldBe(Condition.visible);
        List<Film> films = getFilms();
        for (Film f : films) {
            if (f.getTitle().equalsIgnoreCase(filmTitle)) {
                if (f.getImdbUrl() != null && !f.getImdbUrl().isEmpty()) {
                    Selenide.open(f.getImdbUrl());
                    return true;
                } else {
                    // fallback: try to click anchor inside grid cell that contains title
                    ElementsCollection cellElements = grid.$$(By.cssSelector("vaadin-grid-cell-content"));
                    for (int i = 0; i + 3 < cellElements.size(); i += 4) {
                        if (cellElements.get(i).getText().trim().equalsIgnoreCase(filmTitle)) {
                            try {
                                SelenideElement linkCell = cellElements.get(i + 3);
                                SelenideElement a = linkCell.$("a");
                                a.shouldBe(Condition.visible).click();
                                return true;
                            } catch (Exception e) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Sorts grid by Title by clicking the header sorter.
     */
    public void sortByTitle() {
        try {
            sorterTitle.shouldBe(Condition.visible).click();
        } catch (Exception e) {
            // no-op if sorter not found
        }
    }
}
