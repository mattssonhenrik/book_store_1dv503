package book_store_1dv503.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

import book_store_1dv503.view.BrowseView;
import book_store_1dv503.controller.CartController;
import book_store_1dv503.model.Cart;

public class BrowseController {
  BrowseView browseView;
  CartController cartController;
  boolean running = true;
  boolean subjectLoop = true;
  boolean bookLoop = true;
  boolean ATLoop = true;
  int currentPagination = 0;
  int ATPagination = 0;
  Cart cart;
  String url = "jdbc:mysql://localhost:3306/book_store";
  String user = "root";
  String password = "root";

  public BrowseController(int userId) {
    browseView = new BrowseView();
    cartController = new CartController(userId);
    this.cart = new Cart(userId);
    startBrowseMenu();
  }

  public void startBrowseMenu() {
    while (running) {
      String option = browseView.showBrowseMenu();
      running = true;
      subjectLoop = true;
      bookLoop = true;
      ATLoop = true;
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
          break;
        case "2":
          while (ATLoop) {
            String authorChoice;
            String titleChoice;
            String searchByAuthorOrTitle = browseView.showBrowseByAuthorAndTitleMenu();
            if (searchByAuthorOrTitle.equals("1")) {
              authorChoice = browseView.getAuthorChoice();
              while (bookLoop) {
                sqlGetBookByAuthor(authorChoice, ATPagination);
                ATPagination += 3;
              }
            } else if (searchByAuthorOrTitle.equals("2")) {
              titleChoice = browseView.getTitleChoice();
              while (bookLoop) {
                sqlGetBookByTitle(titleChoice, ATPagination);
                ATPagination += 3;
              }
            }
          }
          break;
        case "3":
          cartController.handleCart();
          break;
        case "4":
          running = false;
          break;
        default:
          System.out.println("Not a valid option.");
          break;
      }
    }
  }

  public void sqlQuerySubject() {
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
        System.out.println(
            "State ISBN if you want to add book to cart, 'n' + 'Enter' for more book options or just 'ENTER' to go back");
        String moreBooks;
        boolean paginationLoop = true;
        while (paginationLoop) {
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
            String ISBN = checkIfGivenStringIsValidISBN(moreBooks);
            if (ISBN != null) {
              getBookByISBN(ISBN);
              Boolean addBook = browseView.addBookByISBN();
              if (addBook) {
                int quantityOfBooks = browseView.addHowManyBooksToCart();
                cart.addBook(ISBN, quantityOfBooks);
                saveCartToDatabase(cart);
                System.out.println("\n");
              } else {
                paginationLoop = true;
                continue;
              }
            } else {
              System.out.println("Wrong input");
            }
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

  public void sqlGetBookByAuthor(String authorChoice, int ATpagination) {
    String query = "SELECT * FROM books where author like '%" + authorChoice + "%' LIMIT 3 OFFSET " + ATpagination;
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

        System.out.println("ISBN: " + isbn + ", \nTitle: " + title + ", \nAuthor: " + author + ", \nPrice: " + price
            + ", \nSubject: " + subject);
        System.out.println("\n");
      }
      if (resultSet.isAfterLast()) {
        System.out.println(
            "State ISBN if you want to add book to cart, 'n' + 'Enter' for more book options or just 'ENTER' to go back");
        String moreBooks;
        boolean paginationLoop = true;
        while (paginationLoop) {
          moreBooks = browseView.getBookChoice();
          paginationLoop = false;
          if (moreBooks.equalsIgnoreCase("n")) {
            paginationLoop = false;
            return;
          } else if (moreBooks.equalsIgnoreCase("")) {
            ATLoop = false;
            bookLoop = false;
            paginationLoop = false;
          } else {
            String ISBN = checkIfGivenStringIsValidISBN(moreBooks);
            if (ISBN != null) {
              getBookByISBN(ISBN);
              Boolean addBook = browseView.addBookByISBN();
              if (addBook) {
                int quantityOfBooks = browseView.addHowManyBooksToCart();
                cart.addBook(ISBN, quantityOfBooks);
                saveCartToDatabase(cart);
              } else {
                paginationLoop = true;
                continue;
              }
            } else {
              System.out.println("Wrong input");
            }
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

  public void sqlGetBookByTitle(String titleChoice, int ATpagination) {
    String query = "SELECT * FROM books where title like '%" + titleChoice + "%' LIMIT 3 OFFSET " + ATpagination;
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
        System.out.println("ISBN: " + isbn + ", \nTitle: " + title + ", \nAuthor: " + author + ", \nPrice: " + price
            + ", \nSubject: " + subject);
        System.out.println("\n");
      }
      if (resultSet.isAfterLast()) {
        System.out.println(
            "State ISBN if you want to add book to cart, 'n' + 'Enter' for more book options or just 'ENTER' to go back");
        String moreBooks;
        boolean paginationLoop = true;
        while (paginationLoop) {
          moreBooks = browseView.getBookChoice();
          paginationLoop = false;
          if (moreBooks.equalsIgnoreCase("n")) {
            paginationLoop = false;
            return;
          } else if (moreBooks.equalsIgnoreCase("")) {
            ATLoop = false;
            bookLoop = false;
            paginationLoop = false;
          } else {
            String ISBN = checkIfGivenStringIsValidISBN(moreBooks);
            if (ISBN != null) {
              getBookByISBN(ISBN);
              Boolean addBook = browseView.addBookByISBN();
              if (addBook) {
                int quantityOfBooks = browseView.addHowManyBooksToCart();
                cart.addBook(ISBN, quantityOfBooks);
                saveCartToDatabase(cart);
              } else {
                paginationLoop = true;
                continue;
              }
            } else {
              System.out.println("Wrong input");
            }
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

  public String checkIfGivenStringIsValidISBN(String statedISBN) {
    String query = "SELECT * FROM Books WHERE isbn = " + '"' + statedISBN + '"';
    try (
        Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query)) {
      if (resultSet.isBeforeFirst()) {
        System.out.println("It is a book!");
        return statedISBN;
      } else {
        System.out.println("Not a book");
        return null;
      }
    } catch (Exception e) {
      System.out.println("Database connection failed!");
      e.printStackTrace();
    }
    return null;
  }

  public void getBookByISBN(String ISBN) {
    String query = "SELECT * FROM books where isbn = " + ISBN;
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
        System.out.println("ISBN: " + isbn + ", \nTitle: " + title + ", \nAuthor: " + author + ", \nPrice: " + price
            + ", \nSubject: " + subject);
        System.out.println("\n");
      }
    } catch (Exception e) {
      System.out.println("Database connection failed!");
      e.printStackTrace();
    }
  }

  public void saveCartToDatabase(Cart cart) {
    try (
        Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement()) {
      for (Map.Entry<String, Integer> entry : cart.getBooks().entrySet()) {
        String isbn = entry.getKey();
        int quantity = entry.getValue();

        String query = "INSERT INTO cart (userid, isbn, qty) VALUES ("
            + cart.getUserId() + ", '"
            + isbn + "', "
            + quantity + ") "
            + "ON DUPLICATE KEY UPDATE qty = " + quantity;
        statement.executeUpdate(query);
      }
      System.out.println("Cart updated successfully!");

    } catch (Exception e) {
      System.out.println("Database connection failed!");
      e.printStackTrace();
    }
  }
}
