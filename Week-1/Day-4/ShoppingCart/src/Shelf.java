import java.util.ArrayList;
import java.util.List;

public class Shelf<T extends Book> {
    private final List<T> books;

    public Shelf() {
        this.books = new ArrayList<>();
    }

    public void addBook(T book) {
        books.add(book);
    }

    public List<T> getBooks() {
        return books;
    }
}