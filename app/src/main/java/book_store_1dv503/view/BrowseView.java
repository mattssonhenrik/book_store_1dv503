package book_store_1dv503.view;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class BrowseView {
  Scanner scanner;

  public BrowseView() {
    this.scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());
  }

  // --------------------------------- Main Browse Menu
  // ---------------------------------
  public String showBrowseMenu() {
    System.out.println(".__________________________________________.");
    System.out.println("|     Welcome to the Online Book Store     |");
    System.out.println("|                Member Menu               |");
    System.out.println("|__________________________________________|");
    System.out.println("|                                          |");
    System.out.println("| 1. BROWSE BY SUBJECT                     |");
    System.out.println("| 2. SEARCH BY AUTHOR/TITLE                |");
    System.out.println("| 3. CHECK OUT                             |");
    System.out.println("| 4. LOGUOUT                               |");
    System.out.println("|__________________________________________|");

    String userChoice = readUserInput();
    return userChoice;
  }

  public String readUserInput() {
    String userChoice;
    while (true) {
      System.out.println("Please enter your choice");

      userChoice = scanner.nextLine().trim();
      if (userChoice.equals("1") || userChoice.equals("2") || userChoice.equals("3") || userChoice.equals("4")) {
        break;
      }
      System.out.println("Invalid input, please enter 1, 2, 3 or 4!");
    }
    return userChoice;
  }

  // --------------------------------- Browse By Subject
  // ---------------------------------
  public void showBrowseBySubjectMenu() {
    System.out.println(".______________________________________________.");
    System.out.println("|     Welcome to the Online Book Store         |");
    System.out.println("|             Browse by Subject                |");
    System.out.println("|______________________________________________|");
    System.out.println("|                                              |");
    System.out.println("| 1. ENTER ISBN TO PUT IN THE CART             |");
    System.out.println("| 2. PRESS `ENTER` TO RETURN TO THE MAIN MENU  |");
    System.out.println("| 3. PRESS `n` + `ENTER` TO CONTINUE BROWISING |");
    System.out.println("|______________________________________________|");
  }

  public String getSubjectFromUser() {
    System.out.println("___________________________________________");
    System.out.println("*** Please enter your preferred Subject ***");
    System.out.println("___________________________________________");
    String userChoice = scanner.nextLine().trim().toLowerCase();
    return userChoice;
  }

  public String getBookChoice() {
    String userChoice = scanner.nextLine().trim().toLowerCase();
    return userChoice;
  }

  public void printSubject(String subject) {
    System.out.println("Subject: " + subject);
  }

  // --------------------------------- Search By Author/Title
  // ---------------------------------
  public String showBrowseByAuthorAndTitleMenu() {
    System.out.println(".__________________________________________.");
    System.out.println("|     Welcome to the Online Book Store     |");
    System.out.println("|          Search by Author/Title          |");
    System.out.println("|__________________________________________|");
    System.out.println("|                                          |");
    System.out.println("| 1. BROWSE BY AUTHOR                      |");
    System.out.println("| 2. SEARCH BY TITLE                       |");
    System.out.println("| 3. BACK                                  |");
    System.out.println("|__________________________________________|");

    String userChoice = readUserInput();
    return userChoice;
  }

  public String getAuthorChoice() {
    System.out.println("Which author are you interested in?");
    String userChoice = scanner.nextLine().trim().toLowerCase();
    return userChoice;
  }

  public String getTitleChoice() {
    System.out.println("Which title are you interested in?");
    String userChoice = scanner.nextLine().trim().toLowerCase();
    return userChoice;
  }

  public Boolean addBookByISBN() {
    System.out.println("Do you want to add this book to your Cart?");
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

  public int addHowManyBooksToCart() {
    int bookQuantity = 0;
    System.out.println("How many Books do you want to Add?");
    while (bookQuantity <= 0) {
      if (scanner.hasNextInt()) {
        bookQuantity = scanner.nextInt();
        scanner.nextLine();
        if (bookQuantity <= 0) {
          System.out.println("You have to state a positive number");
      }
      } else {
        scanner.nextLine();
        System.out.println("You have to state a positive number");
      }
    }
    return bookQuantity;
  }

}
