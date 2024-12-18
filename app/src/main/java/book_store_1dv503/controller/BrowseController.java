package book_store_1dv503.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import book_store_1dv503.view.BrowseView;

public class BrowseController {
  public BrowseController() {
    System.out.println("Hi from BrowseController");
    startBrowseMenu();
  }

  public void startBrowseMenu() {
    boolean running = true;
    while (running) {
      BrowseView browseView = new BrowseView();
      String option = browseView.showBrowseMenu();
      switch (option) {
        case "1":

          System.out.println("Please enter your choice 1");
          break;
        case "2":
          System.out.println("Please enter your choice 2");
          break;
        case "3":
          System.out.println("Please enter your choice 3");
          break;
        case "4":
          System.out.println("Please enter your choice 4");
          running = false;
          break;
        default:
          System.out.println("Not a valid option.");
          break;
      }
    }
  }

  public void SQLCALL() {
    String url = "jdbc:mysql://localhost:3306/book_store";
    String user = "root";
    String password = "root";

    String query = "SELECT * FROM books limit 5";

    try (
        Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query)) {

      System.out.println("Connected to the database!");

      while (resultSet.next()) {
        String isbn = resultSet.getString("isbn");
        String title = resultSet.getString("title");
        String author = resultSet.getString("author");
        double price = resultSet.getDouble("price");
        String subject = resultSet.getString("subject");

        System.out.println("ISBN: " + isbn + ", Title: " + title + ", Author: " + author + ", Price: " + price
            + ", Subject: " + subject);
      }

    } catch (Exception e) {
      System.out.println("Database connection failed!");
      e.printStackTrace();
    }
  }
}
