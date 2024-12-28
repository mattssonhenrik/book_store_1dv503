package book_store_1dv503.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import book_store_1dv503.view.MemberMenuView;
import book_store_1dv503.model.Member;

public class MemberController {
  BrowseController browseController;
  Member member;
  boolean running = true;
  MemberMenuView memberMenuView;
  String url = "jdbc:mysql://localhost:3306/book_store";
  String user = "root";
  String password = "root";

  public MemberController() {
    startMemberMenu();
  }

  public void startMemberMenu() {
    while (running) {
      memberMenuView = new MemberMenuView();
      String option = memberMenuView.showMemberMenu();
      switch (option) {
        case "1":
          loginMember();
          break;
        case "2":
          registerMember();
          break;
        case "q":
          running = false;
          break;
        default:
          System.out.println("Not a valid option.");
          break;
      }
    }
  }

  public void loginMember() {
    String email = memberMenuView.getEmail();
    String password = memberMenuView.getPassword();
    String databasePassword = getEmailPasswordFromDatabase(email);

    if (password.equalsIgnoreCase(databasePassword)) {
      System.out.println("Login SUCCESS");
      int userId = getUserIdByEmail(email); 
      browseController = new BrowseController(userId);
    } else {
      System.out.println("Wrong password!");
    }

  }

  public String getEmailPasswordFromDatabase(String email) {
    String query = "SELECT password FROM Members WHERE email = " + '"' + email + '"';
    try (
        Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query)) {
      if (!resultSet.isBeforeFirst()) {
        System.out.println("The email does not exist");
        return null;
      } else {
        resultSet.next();
        return resultSet.getString("password");
      }
    } catch (Exception e) {
      System.out.println("Database connection failed!");
      e.printStackTrace();
    }
    return null;
  }

  public void registerMember() {
    String firstName = memberMenuView.getFirstName();
    String lastName = memberMenuView.getLastName();
    String address = memberMenuView.getAddress();
    String city = memberMenuView.getCity();
    int zip = memberMenuView.getZip();
    String phone = memberMenuView.getPhone();
    boolean uniqueEmail = false;
    String email;
    do {
      email = memberMenuView.getEmail();
      uniqueEmail = checkForUniqueEmail(email);
    } while (!uniqueEmail);
    String password = memberMenuView.getPassword();
    member = new Member(firstName, lastName, address, city, zip, phone, email, password);
    saveMemberToDatabase(member);
  }

  public boolean checkForUniqueEmail(String email) {
    String query = "SELECT * FROM Members WHERE email = " + '"' + email + '"';
    try (
        Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query)) {
      if (!resultSet.isBeforeFirst()) {
        System.out.println("The email is unique");
        return true;
      } else {
        System.out.println("The email is NOT unique");
        return false;
      }
    } catch (Exception e) {
      System.out.println("Database connection failed!");
      e.printStackTrace();
    }
    return false;
  }

  public void saveMemberToDatabase(Member member) {
    String query = "INSERT INTO members (fname, lname, address, city, zip, phone, email, password) VALUES ('"
        + member.getFirstName() + "', '"
        + member.getLastName() + "', '"
        + member.getAddress() + "', '"
        + member.getCity() + "', "
        + member.getZip() + ", '"
        + member.getPhone() + "', '"
        + member.getEmail() + "', '"
        + member.getPassword() + "')";
    try (
        Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement()) {
      int rowsAffected = statement.executeUpdate(query);
      if (rowsAffected > 0) {
        System.out.println("Member registered successfully!");
      } else {
        System.out.println("Failed to register the member.");
      }
    } catch (Exception e) {
      System.out.println("Database connection failed!");
      e.printStackTrace();
    }
  }

  public int getUserIdByEmail(String email) {
    String query = "SELECT userid FROM members WHERE email = \"" + email + "\"";
    try (
        Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query)) {
      if (resultSet.next()) {
        return resultSet.getInt("userid");
      }
    } catch (Exception e) {
      System.out.println("Failed to fetch user ID!");
      e.printStackTrace();
    }
    return 0;
  }
}
