package book_store_1dv503.view;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class BrowseView {
  Scanner scanner;

  public BrowseView() {
    this.scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());
  }

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
}
