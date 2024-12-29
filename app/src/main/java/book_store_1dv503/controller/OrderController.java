package book_store_1dv503.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import book_store_1dv503.model.DTO_member_adress;
import book_store_1dv503.model.Order;
import book_store_1dv503.view.InvoiceView;

public class OrderController {
  int grandTotal = 0;
  Order order;
  int orderNumber;
  int userId;
  String shipAddress;
  String shipCity;
  int shipZip;
  String url = "jdbc:mysql://localhost:3306/book_store";
  String user = "root";
  String password = "root";
  InvoiceView invoiceView;

  public OrderController(int userId) {
    this.userId = userId;
    invoiceView = new InvoiceView(userId);

    registerOrder();

    orderNumber = getOrderNumber();
    if (orderNumber != -1) {
      displayInvoiceHeader(orderNumber);
      displayInvoiceBody();
      storeOrderDetails();
    } else {
      System.out.println("Feel free to continue browsing!");
    }
  }

  public void registerOrder() {
    DTO_member_adress memberAddress = getMemberAddress();
    shipAddress = memberAddress.getAddress();
    shipCity = memberAddress.getCity();
    shipZip = memberAddress.getZip();
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
    String query = "SELECT ono FROM orders WHERE userid = " + userId + " ORDER BY ono DESC LIMIT 1";
    try (
        Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query)) {
      if (resultSet.next()) {
        int orderNumber = resultSet.getInt("ono");
        return orderNumber;
      } else {
        System.out.println("No order number to be found!");
        return -1;
      }
    } catch (Exception e) {
      System.out.println("Database connection failed!");
      e.printStackTrace();
    }
    return -1;
  }

  public void displayInvoiceHeader(int orderNumber) {
    String query = "select * from cart join books ON cart.isbn = books.isbn join members ON cart.userid = members.userid  WHERE members.userId = " + '"' + userId + '"';
    try (
        Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query)) {
      if (resultSet.isBeforeFirst()) {
          resultSet.next();
          String firstName = resultSet.getString("fname");
          String lastName = resultSet.getString("lname");
          shipAddress = resultSet.getString("address");
          shipCity = resultSet.getString("city");
          shipZip = resultSet.getInt("zip");
          System.out.println("\n");
          System.out.println("\n");
          System.out.println("Invoice for order number: " + orderNumber);
          System.out.println("Name: " + firstName + " " + lastName + ", \nShipping Adress: " + "\n-" + shipCity + "\n-" + shipAddress + "\n-" + shipZip);
          System.out.println("\n");
      } else {
      }
    } catch (Exception e) {
      System.out.println("Database connection failed!");
      e.printStackTrace();
    }
    System.out.println("This is your ordernumber " + orderNumber);
  }

  public void displayInvoiceBody() {
    String query = "select * from cart join books ON cart.isbn = books.isbn WHERE userId = " + '"' + userId + '"';
    try (
        Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query)) {
      if (resultSet.isBeforeFirst()) {
        while (resultSet.next()) {
          String isbn = resultSet.getString("isbn");
          String title = resultSet.getString("title");
          int qty = resultSet.getInt("qty");
          float price = resultSet.getFloat("price");
          System.out.println("----------------------------------------------------------------------------------------------------");
          System.out.println("ISBN: " + isbn + ", \nQuantity: " + qty + ", \nTitle: " + title + ", \nPrice per book: " + price);
          grandTotal += (price*qty);
        }
        System.out.println("====================================================================================================");
        System.out.println("Grand total is: "+grandTotal);
        System.out.println("====================================================================================================");

        invoiceView.pressAnyKeyToContinue();
        deleteCartForOrder();
      } else {
        System.out.println("Nothing in the cart yet");
      }
    } catch (Exception e) {
      System.out.println("Database connection failed!");
      e.printStackTrace();
    }
  }

  public void storeOrderInDatabase(Order order) {
    String query = "INSERT INTO orders (shipAddress, shipCity, userid, shipzip, created) VALUES ('"
        + order.getShipAddress() + "', '"
        + order.getShipCity() + "', "
        + userId + ", "
        + order.getShipZip() + ", "
        + "CURDATE())";
    try (
        Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement()) {
      int rowsAffected = statement.executeUpdate(query);
      if (rowsAffected > 0) {
        System.out.println("Order registered successfully!");
      } else {
        System.out.println("Failed to register the order.");
      }
    } catch (Exception e) {
      System.out.println("Database connection failed!");
      e.printStackTrace();
    }
  }

  public void deleteCartForOrder() {
    System.out.println("Not deleting anything yet, bud!!");
  }

  public void storeOrderDetails() {
    String query = "SELECT * FROM cart join books ON cart.isbn = books.isbn WHERE userId = " + '"' + userId + '"';
    try (
        Connection connection = DriverManager.getConnection(url, user, password);
        Statement selectStatement = connection.createStatement();
        Statement insertStatement = connection.createStatement();
        ResultSet resultSet = selectStatement.executeQuery(query)) {
      while (resultSet.next()) {
        String isbn = resultSet.getString("isbn");
        int qty = resultSet.getInt("qty");
        float price = resultSet.getFloat("price");
        float grandTotal = qty * price;

        String insertQuery = "INSERT INTO odetails (ono, isbn, qty, amount) VALUES ("
            + orderNumber + ", '"
            + isbn + "', "
            + qty + ", "
            + grandTotal + ")";

        insertStatement.executeUpdate(insertQuery);
      }
      System.out.println("Orderdetails stored!");
    } catch (Exception e) {
      System.out.println("Database connection failed!");
      e.printStackTrace();
    }
  }
}
