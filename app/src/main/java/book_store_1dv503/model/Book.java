package book_store_1dv503.model;

public class Book {
  private String isbn;
  private String author;
  private String title;
  private float price;
  private String subject;

  public Book (String isbn, String author, String title, float price, String subject) {
    this.isbn = isbn;
    this.author = author;
    this.title = title;
    this.price = price;
    this.subject = subject;
  }

  /*
    public String getIsbn() {
     return isbn; 
     }
    public String getAuthor() { 
    return author; 
    }
    public String getTitle() {
     return title; 
     }
    public float getPrice() {
     return price; 
     }
    public String getSubject() { 
    return subject;
    } 
    */
  
}
