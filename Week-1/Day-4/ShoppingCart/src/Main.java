import java.util.List;

public class Main {
    public static void main(String[] args) {
        Book book1 = new TestBook("first", "Murakami", 1997);
        Book book2 = new TestBook("sec", "Haruki", 2003);

        Shelf<Book> shelf = new Shelf<>();
        shelf.addBook(book1);
        shelf.addBook(book2);

        List<Book> books = (List<Book>) shelf.getBooks();

        books.forEach(book -> System.out.println("Title: " + book.getName() +
                ", Author: " + book.getAuthor() +
                ", Year: " + book.getYear()));
    }
}