package hcmute.edu.vn.nhom20.foodie.model;

public class Cart {
    private Integer Id;
    private String UserName;
    private Integer idProduct;
    private Integer quantity;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public Integer getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Cart(Integer id, String userName, Integer idProduct, Integer quantity) {
        Id = id;
        UserName = userName;
        this.idProduct = idProduct;
        this.quantity = quantity;
    }
}
