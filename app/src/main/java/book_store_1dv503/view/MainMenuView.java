package book_store_1dv503.view;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class MainMenuView {
  Scanner scanner;

  public MainMenuView() {
    this.scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());
  }

  public int showMenu() {
    System.out.println(".__________________________________________.");
    System.out.println("|     Welcome to the Online Book Store     |");
    System.out.println("|__________________________________________|");
    System.out.println("|                                          |");
    System.out.println("| 1. MEMBER LOGIN                          |");
    System.out.println("| 2. NEW MEMBER REGISTRATION               |");
    System.out.println("| 3. QUIT                                  |");
    System.out.println("|__________________________________________|");

    int userChoice = readUserInput();
    return userChoice;
  }

  public int readUserInput() {
    while (!scanner.hasNextInt()) {
      scanner.nextLine();
      System.out.println("Invalid input, please enter an integer!");
    }
    int userChoice = scanner.nextInt();
    scanner.nextLine();
    return userChoice;
  }
}
