package book_store_1dv503;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/book_store"; 
        String user = "root";  
        String password = "root";  

        String query = "SELECT * FROM books limit 5";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            System.out.println("Connected to the database!");

            while (resultSet.next()) {
                String isbn = resultSet.getString("isbn");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                double price = resultSet.getDouble("price");
                String subject = resultSet.getString("subject");

                System.out.println("ISBN: " + isbn + ", Title: " + title + ", Author: " + author + ", Price: " + price + ", Subject: " + subject);
            }

        } catch (Exception e) {
            System.out.println("Database connection failed!");
        }
    }
  }

