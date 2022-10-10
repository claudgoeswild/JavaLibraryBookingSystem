
package user;

import java.util.ArrayList;
import library.Book;

public class Grad_student extends Person {
    private String ra;
    
    public Grad_student(String personName, String email, String password, String ra) {
        super(personName, email, password);
        this.ra = ra;
    }
    public Grad_student(){
        
    }

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public ArrayList<Book> getBookList() {
        return bookList;
    }

    public void setBookList(ArrayList<Book> bookList) {
        this.bookList = bookList;
    }
    
}
