
package user;

import java.util.ArrayList;
import library.Book;

public class Postgrad_student extends Person{
    private String coo;

    public Postgrad_student(String personName, String email, String password, String coo) {
        super(personName, email, password);
        this.coo = coo;
    }

    public String getCoo() {
        return coo;
    }

    public void setCoo(String coo) {
        this.coo = coo;
    }

    public ArrayList<Book> getBookList() {
        return bookList;
    }

    public void setBookList(ArrayList<Book> bookList) {
        this.bookList = bookList;
    }
    

    public Postgrad_student() {
    }
}
