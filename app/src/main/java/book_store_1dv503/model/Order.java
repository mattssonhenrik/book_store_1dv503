package book_store_1dv503.model;

import java.time.LocalDate;

public class Order {
  private int userId;
  private int orderNumber;
  private LocalDate created;
  private String shipAddress;
  private String shipCity;
  private int shipZip;

  public Order (int userId, int orderNumber, LocalDate created, String shipAddress, String shipCity, int shipZip) {
    this.userId = userId;
    this.orderNumber = orderNumber;
    this.created = created;
    this.shipAddress = shipAddress;
    this.shipZip = shipZip;
  }

}
