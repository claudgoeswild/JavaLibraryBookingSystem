
package library;
import java.util.ArrayList;

public class Publisher {
    private String publisherName;
    private String address;
    ArrayList<Book> bookList;

    public Publisher(String publisherName, String address) {
        this.publisherName = publisherName;
        this.address = address;
        bookList = new ArrayList<>();
    }

    public Publisher() {
        bookList = new ArrayList<>();
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }    

    public ArrayList<Book> getBookList() {
        return bookList;
    }

    public void setBookList(ArrayList<Book> bookList) {
        this.bookList = bookList;
    }
    public void addBook(Book b){
        b.setPublisher(this);
        bookList.add(b);
        
    }
}
