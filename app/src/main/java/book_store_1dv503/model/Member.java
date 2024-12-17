package book_store_1dv503.model;

public class Member {
  private String fname;
  private String lname;
  private String address;
  private String city;
  private int zip;
  private String phone;
  private String email;
  private int userId;
  private String password;

  public Member(String fname, String lname, String address, String city, int zip, String phone, String email, int userId, String password) {
    this.fname = fname;
    this.lname = lname;
    this.address = address;
    this.city = city;
    this.zip = zip;
    this.phone = phone;
    this.email = email;
    this.userId =userId;
    this.password = password;
  }
}
