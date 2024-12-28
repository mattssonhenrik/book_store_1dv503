package book_store_1dv503.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class OrderController {
  int orderNumber;
  int userId;
  String url = "jdbc:mysql://localhost:3306/book_store";
  String user = "root";
  String password = "root";

  public OrderController(int userId) {
    this.userId = userId;
    orderNumber = getOrderNumber();

    displayInvoice(orderNumber); 
    storeOrderInDatabase();
  }

  public int getOrderNumber () {
        String query = "SELECT * FROM cart join books ON cart.isbn = books.isbn WHERE userId = " + '"' + userId + '"';
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

  public void storeOrderInDatabase() {

    deleteCartForOrder();
  }

  public void deleteCartForOrder() {

  }
}
