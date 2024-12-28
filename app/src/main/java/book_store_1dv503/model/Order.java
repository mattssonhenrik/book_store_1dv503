package book_store_1dv503.model;

import java.time.LocalDate;

public class Order {
  private int userId;
  private LocalDate created;
  private String shipAddress;
  private String shipCity;
  private int shipZip;

  public Order (int userId, String shipAddress, String shipCity, int shipZip) {
    this.userId = userId;
    this.created = LocalDate.now();
    this.shipAddress = shipAddress;
    this.shipCity = shipCity;
    this.shipZip = shipZip;
  }

  public LocalDate getCreated () {
    return created;
  }
  
  public String getShipAddress () {
    return shipAddress;
  }

  public String getShipCity () {
    return shipCity;
  }

  public int getShipZip () {
    return shipZip;
  }



}
