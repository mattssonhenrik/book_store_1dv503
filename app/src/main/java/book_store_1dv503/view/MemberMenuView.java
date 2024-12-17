package book_store_1dv503.view;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class MemberMenuView {
    Scanner scanner;

    public MemberMenuView () {
    this.scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());
    }

    public String showMemberMenu() {
      System.out.println(".__________________________________________.");
      System.out.println("|     Welcome to the Online Book Store     |");
      System.out.println("|__________________________________________|");
      System.out.println("|                                          |");
      System.out.println("| 1. MEMBER LOGIN                          |");
      System.out.println("| 2. NEW MEMBER REGISTRATION               |");
      System.out.println("| q. QUIT                                  |");
      System.out.println("|__________________________________________|");
  
      String userChoice = readUserInput();
      return userChoice;
    }
    public String readUserInput() {
      String userChoice;
      while (true) {
          System.out.println("Please enter your choice");

          userChoice = scanner.nextLine().trim(); 
          if (userChoice.equals("1") || userChoice.equals("2") || userChoice.equalsIgnoreCase("q")) {
              break; 
          }
          System.out.println("Invalid input, please enter 1, 2, or 'q'!");
      }
      return userChoice; 
  }
}
