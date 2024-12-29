package book_store_1dv503.view;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;


public class InvoiceView {
  Scanner scanner;
  String url = "jdbc:mysql://localhost:3306/book_store";
  String user = "root";
  String password = "root";
  int userId;
  float grandTotal;

  public InvoiceView(int userId) {
    this.scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());
    this.userId = userId;
  }

  public boolean showCartAndAskIfCheckOut() {
    System.out.println(".__________________________________________.");
    System.out.println("|     Welcome to the Online Book Store     |");
    System.out.println("|                 Cart Menu                |");
    System.out.println("|__________________________________________|");

    boolean somethingInCart = getCart();
    boolean userChoice;
    if (somethingInCart) {
      userChoice = readUserInput();
      return userChoice;
    } else {
      userChoice = false;
      return userChoice;
    }
  }

  public boolean readUserInput() {
    System.out.println("Do you want to check out (y/n)?");
    while (true) {
      String userChoice = scanner.nextLine().trim().toLowerCase();
      if (userChoice.equals("y") || userChoice.equals("yes")) {
        return true;
      } else if (userChoice.equals("n") || userChoice.equals("no")) {
        return false;
      } else {
        System.out.println("You have to chose either 'y' (yes) or 'n' (no)");
      }
    }
  }

  public void pressAnyKeyToContinue() {
    System.out.println("Press 'Enter' to continue browsing.");
      String userChoice = scanner.nextLine();
  }

  public boolean getCart() {
        String query = "SELECT * FROM cart join books ON cart.isbn = books.isbn WHERE userId = " + '"' + userId + '"';
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
          System.out.println("ISBN: " + isbn + ", \nQuantity: " + qty + ", \nTitle: " + title + ", \nPrice per book: " + price);
          System.out.println("\n");
          grandTotal += (price*qty);
        }
        System.out.println("Grand total is: "+grandTotal);
      } else {
        System.out.println("Nothing in the cart yet");
        return false;
      }
    } catch (Exception e) {
      System.out.println("Database connection failed!");
      e.printStackTrace();
    }
    return true;
  }
}
