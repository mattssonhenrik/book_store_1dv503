package book_store_1dv503.controller;

import book_store_1dv503.view.MemberMenuView;

public class MemberController {
  BrowseController browseController;
  boolean running = true;

  public MemberController() {
    startMemberMenu();
  }

  public void startMemberMenu() {
    while (running) {
      MemberMenuView memberMenuView = new MemberMenuView();
      String option = memberMenuView.showMemberMenu();
      switch (option) {
        case "1":
          loginMember();
          break;
        case "2":
          registerMember();
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
    // Something something login member, try/catch?
    // If successful
    browseController = new BrowseController();
  } 

  public void registerMember() {
    // Something something login member, try/catch?
    // If successful
    browseController = new BrowseController();
  }
}
