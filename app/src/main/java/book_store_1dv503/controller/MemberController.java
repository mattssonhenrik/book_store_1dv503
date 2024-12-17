package book_store_1dv503.controller;

import book_store_1dv503.view.MemberMenuView;

public class MemberController {
  BrowseController browseController;

  public MemberController () {

    startMemberMenu();
  }

  public void startMemberMenu() {
    boolean running = true;
    while (running) {
      MemberMenuView memberMenuView = new MemberMenuView();
      String option = memberMenuView.showMemberMenu();
      switch (option) {
        case "1":
        // Funktion som loggar in medlemmen
        loginMember();
        System.out.println("Please enter your choice 1");
          break;
        case "2":
        System.out.println("Please enter your choice 2");
          break;
        case "q":
        System.out.println("Please enter your choice q");
        running = false;
          break;
        default:
          System.out.println("Not a valid option.");
          break;
      }
    }
  }

  public void loginMember() {
    //Something something login member, try/catch?
    //If successful
    browseController = new BrowseController();
  }
}
