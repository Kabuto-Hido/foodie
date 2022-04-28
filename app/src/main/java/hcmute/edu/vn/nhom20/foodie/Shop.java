package hcmute.edu.vn.nhom20.foodie;

public class Shop {
    private String name;
    private byte[] image;
    private String address;
    private String phone;

    public Shop(String name, byte[] image, String address, String phone) {
        this.name = name;
        this.image = image;
        this.address = address;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
