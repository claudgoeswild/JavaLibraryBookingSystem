
package library;

public class Book {
    private String title;
    private String author;
    private Publisher publisher = new Publisher();
    private String available = "Sim";

    public Book(String title, String author, String available) {
        this.title = title;
        this.author = author;
        this.available = available;
    }

    public Book() {
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }
    
}
