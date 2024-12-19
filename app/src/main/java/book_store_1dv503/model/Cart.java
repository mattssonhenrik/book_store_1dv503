package book_store_1dv503.model;

import java.util.HashMap;

public class Cart {
  private int userId;
  private HashMap<String, Integer> books; 

  public Cart(int userId) {
      this.userId = userId;
      this.books = new HashMap<>();
  }

  public int getUserId() {
      return userId;
  }

  public HashMap<String, Integer> getBooks() {
      return books;
  }

  public void addBook(String isbn, int quantity) {
      books.put(isbn, books.getOrDefault(isbn, 0) + quantity);
  }

  public void removeBook(String isbn) {
      books.remove(isbn);
  }
}