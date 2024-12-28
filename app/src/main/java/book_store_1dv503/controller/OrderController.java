package book_store_1dv503.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import book_store_1dv503.model.DTO_member_adress;
import book_store_1dv503.model.Order;

public class OrderController {
  Order order;
  int orderNumber;
  int userId;
  String url = "jdbc:mysql://localhost:3306/book_store";
  String user = "root";
  String password = "root";

  public OrderController(int userId) {
    this.userId = userId;

    registerOrder();

    orderNumber = getOrderNumber();
    displayInvoice(orderNumber);
  }

    public void registerOrder() {
    DTO_member_adress memberAddress = getMemberAddress();
    String shipAddress = memberAddress.getAddress();
    String shipCity = memberAddress.getCity();
    int shipZip = memberAddress.getZip();
    order = new Order(userId, shipAddress, shipCity, shipZip);
    storeOrderInDatabase(order);
  }

  public DTO_member_adress getMemberAddress() {
    String query = "SELECT * FROM members WHERE userId = " + '"' + userId + '"';
    try (
        Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query)) {
      if (resultSet.isBeforeFirst()) {
        while (resultSet.next()) {
          String address = resultSet.getString("address");
          String city = resultSet.getString("city");
          int zip = resultSet.getInt("zip");
          DTO_member_adress dto_member_adress = new DTO_member_adress(address, city, zip);
          return dto_member_adress;
        }
      }
    } catch (Exception e) {
      System.out.println("Database connection failed!");
      e.printStackTrace();
    }
    return null;
  }





  public int getOrderNumber() {
    String query = "SELECT * FROM orders join books ON cart.isbn = books.isbn WHERE userId = " + '"' + userId + '"';
    try (
        Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query)) {
      if (resultSet.isBeforeFirst()) {
        while (resultSet.next()) {
          int orderNumber = resultSet.getInt("ono");
        }
        return orderNumber;
      }
    } catch (Exception e) {
      System.out.println("Database connection failed!");
      e.printStackTrace();
    }
  }

  public void displayInvoice(int orderNumber) {
    System.out.println("This is your ordernumber " + orderNumber);
  }

  public void storeOrderInDatabase(Order order) {

    deleteCartForOrder();
  }

  public void deleteCartForOrder() {

  }
}
