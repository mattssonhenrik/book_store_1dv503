package book_store_1dv503.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import book_store_1dv503.view.BrowseView;

public class BrowseController {
  BrowseView browseView;
  boolean running = true;
  boolean subjectLoop = true;
  boolean bookLoop = true;
  int currentPagination = 0;

  public BrowseController() {
    browseView = new BrowseView();
    startBrowseMenu();
  }

  public void startBrowseMenu() {
    BrowseView browseView = new BrowseView();
    while (running) {
      String option = browseView.showBrowseMenu();
      running = true;
      subjectLoop = true;
      bookLoop = true;
      switch (option) {
        case "1":
          while (subjectLoop) {
            sqlQuerySubject();
            String chosenSubject = browseView.getSubjectFromUser();
            bookLoop = true;
            while (bookLoop) {
              sqlGetBooksBySubject(chosenSubject, currentPagination);
              currentPagination += 2;
            }
          } 
          System.out.println("Loop finished!");
          break;
        case "2":
          System.out.println("Browse by Author and Title");
          browseView.showBrowseByAuthorAndTitleMenu();
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

  public void sqlQuerySubject() {
    String url = "jdbc:mysql://localhost:3306/book_store";
    String user = "root";
    String password = "root";

    String query = "SELECT DISTINCT SUBJECT FROM books order by subject asc";

    try (
        Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query)) {
      while (resultSet.next()) {

        String subject = resultSet.getString("subject");
        browseView.printSubject(subject);
      }

    } catch (Exception e) {
      System.out.println("Database connection failed!");
      e.printStackTrace();
    }
  }

  public void sqlGetBooksBySubject(String chosenSubject, int currentPagination) {
    String url = "jdbc:mysql://localhost:3306/book_store";
    String user = "root";
    String password = "root";
    String query = "SELECT * FROM books WHERE subject = '" + chosenSubject + "' LIMIT 2 OFFSET " + currentPagination;

    try (
        Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query)) {

      if (!resultSet.isBeforeFirst()) {
        System.out.println("No books in the subject " + chosenSubject + ", please chose a new subject!");
        System.out.println("\n");
        bookLoop = false;
      } 

      while (resultSet.next()) {
        String isbn = resultSet.getString("isbn");
        String title = resultSet.getString("title");
        String author = resultSet.getString("author");
        double price = resultSet.getDouble("price");
        String subject = resultSet.getString("subject");

        System.out.println("ISBN: " + isbn + ", \nTitle: " + title + ", \nAuthor: " + author + ", \nPrice: " + price
            + ", \nSubject: " + subject);
        System.out.println("\n");
      }

      if (resultSet.isAfterLast()) {
        System.out.println("'n' + 'Enter' for more book options or just 'ENTER' to go back");
        String moreBooks;
        boolean paginationLoop = true;
        while(paginationLoop) {
          moreBooks = browseView.getBookChoice();
          paginationLoop = false;
          if (moreBooks.equalsIgnoreCase("n")) {
            paginationLoop = false;
            return;
          } else if (moreBooks.equalsIgnoreCase("")) {
            subjectLoop = false;
            bookLoop = false;
            paginationLoop = false;
          } else {
            System.out.println("Wrong input");
            // currentPagination -=2;
            paginationLoop = true;
          }
          System.out.println("\n");
        }
      }
    } catch (Exception e) {
      System.out.println("Database connection failed!");
      e.printStackTrace();
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
