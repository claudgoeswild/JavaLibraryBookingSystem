
package user;

import java.util.ArrayList;
import library.Book;

public class Person {
    private String personName;
    private String email;
    private String password;
    protected ArrayList<Book> bookList;
    
    public Person(){
        bookList = new ArrayList<>();
    }
    public Person(String personName, String email, String password) {
        this.personName = personName;
        this.email = email;
        this.password = password;
        bookList = new ArrayList<>();
    }

    public String getPersonName() {
        return personName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Book> getBookList() {
        return bookList;
    }

    public void setBookList(ArrayList<Book> bookList) {
        this.bookList = bookList;
    }
}
