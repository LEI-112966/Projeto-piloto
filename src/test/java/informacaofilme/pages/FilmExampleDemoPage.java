package informacaofilme.pages;
import informacaofilme.Film;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Page object for the film example demo page.
 * Assumes the grid has 4 columns in order: Title, Release Year, Director, IMDB Link.
 */
public class FilmExampleDemoPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By gridBy = By.cssSelector("vaadin-grid");
    private final By cellContentBy = By.cssSelector("vaadin-grid-cell-content");
    private final By sorterTitleBy = By.cssSelector("vaadin-grid-sorter[path='col5']"); // header sorter for Title

    public FilmExampleDemoPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(gridBy));
    }

    /**
     * Reads the visible grid cells and builds a list of Film objects.
     * This implementation groups cell contents in batches of 4 (title, year, director, link).
     */
    public List<Film> getFilms() {
        WebElement grid = driver.findElement(gridBy);
        List<WebElement> cells = grid.findElements(cellContentBy);

        List<Film> films = new ArrayList<>();
        int cols = 4;
        // Defensive: if the header cells are present among cell elements, skip them by filtering anchors/text content heuristics.
        List<String> texts = new ArrayList<>();
        for (WebElement c : cells) {
            String text = c.getText().trim();
            // For link cells there might be an anchor; prefer href if present
            WebElement link = null;
            try {
                link = c.findElement(By.tagName("a"));
            } catch (Exception ignored) {}
            if (link != null && (text.isEmpty() || text.startsWith("Click"))) {
                // normalize to anchor text (anchor href will be captured when grouping)
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
                    WebElement cellElement = cells.get(i + 3);
                    WebElement a = cellElement.findElement(By.tagName("a"));
                    href = a.getAttribute("href");
                } catch (Exception ignored) {}
            }
            if (title.isEmpty() && year.isEmpty() && director.isEmpty() && href.isEmpty()) {
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
        List<Film> films = getFilms();
        for (Film f : films) {
            if (f.getTitle().equalsIgnoreCase(filmTitle)) {
                if (f.getImdbUrl() != null && !f.getImdbUrl().isEmpty()) {
                    driver.get(f.getImdbUrl());
                    return true;
                } else {
                    // fallback: try to click anchor inside grid cell that contains title
                    WebElement grid = driver.findElement(gridBy);
                    List<WebElement> cells = grid.findElements(cellContentBy);
                    for (int i = 0; i + 3 < cells.size(); i += 4) {
                        if (cells.get(i).getText().trim().equalsIgnoreCase(filmTitle)) {
                            try {
                                WebElement linkCell = cells.get(i + 3);
                                WebElement a = linkCell.findElement(By.tagName("a"));
                                wait.until(ExpectedConditions.elementToBeClickable(a)).click();
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
            WebElement sorter = driver.findElement(sorterTitleBy);
            wait.until(ExpectedConditions.elementToBeClickable(sorter)).click();
        } catch (Exception e) {
            // no-op if sorter not found
        }
    }
}
