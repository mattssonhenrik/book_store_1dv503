package book_store_1dv503.model;

import java.util.HashMap;

public class Cart {
  private int userId;
  private HashMap<String, Integer> books;
  private int quantity;

  public Cart(int userId) {
    this.userId = userId;
    this.books = new HashMap<>();
  }
}
