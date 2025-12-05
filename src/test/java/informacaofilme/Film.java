package informacaofilme;
import java.util.Objects;

public class Film {
    private final String title;
    private final String releaseYear;
    private final String director;
    private final String imdbUrl;

    public Film(String title, String releaseYear, String director, String imdbUrl) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.director = director;
        this.imdbUrl = imdbUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public String getDirector() {
        return director;
    }

    public String getImdbUrl() {
        return imdbUrl;
    }

    @Override
    public String toString() {
        return "Film{" +
                "title='" + title + '\'' +
                ", releaseYear='" + releaseYear + '\'' +
                ", director='" + director + '\'' +
                ", imdbUrl='" + imdbUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Film)) return false;
        Film film = (Film) o;
        return Objects.equals(title, film.title) &&
                Objects.equals(releaseYear, film.releaseYear) &&
                Objects.equals(director, film.director) &&
                Objects.equals(imdbUrl, film.imdbUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, releaseYear, director, imdbUrl);
    }
}
