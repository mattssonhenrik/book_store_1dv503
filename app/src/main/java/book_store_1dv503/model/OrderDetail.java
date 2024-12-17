package book_store_1dv503.model;

public class OrderDetail {
  private int orderNumber;
  private String isbn;
  private int quantity;
  private float amount;

  public OrderDetail (int orderNumber, String isbn, int quantity, float amount) {
    this.orderNumber = orderNumber;
    this.isbn = isbn;
    this.quantity = quantity;
    this.amount = amount;
  }
}
