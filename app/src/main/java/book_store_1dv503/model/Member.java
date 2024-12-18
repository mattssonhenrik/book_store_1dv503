package book_store_1dv503.model;

public class Member {
  private String fname;
  private String lname;
  private String address;
  private String city;
  private int zip;
  private String phone;
  private String email;
  private String password;

  public Member(String fname, String lname, String address, String city, int zip, String phone, String email, String password) {
    this.fname = fname;
    this.lname = lname;
    this.address = address;
    this.city = city;
    this.zip = zip;
    this.phone = phone;
    this.email = email;
    this.password = password;
  }

  public String getFirstName() {
    return fname;
  }

  public String getLastName() {
    return lname;
  }

  public String getAddress() {
    return address;
  }

  public String getCity() {
    return city;
  }

  public int getZip() {
    return zip;
  }

  public String getPhone() {
    return phone;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }
}
