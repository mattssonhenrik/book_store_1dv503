package book_store_1dv503.model;

// Data transfer object for moving member class fields to order class fields.
public class DTO_member_adress {
  private String address;
  private String city;
  private int zip;

  public DTO_member_adress(String address, String city, int zip) {
    this.setAddress(address);
    this.setCity(city);
    this.setZip(zip);
  }

  public String getAddress() {
    return address;
  }

  public String getCity() {
    return city;
  }

  public int getZip () {
    return zip;
  }

  public void setAddress (String address) {
    this.address = address;
  }

  public void setCity (String city) {
    this.city = city;
  }

  public void setZip (int zip) {
    this.zip = zip;
  }
}
