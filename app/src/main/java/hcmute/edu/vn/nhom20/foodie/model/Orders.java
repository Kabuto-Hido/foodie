package hcmute.edu.vn.nhom20.foodie.model;

public class Orders {
    private Integer Id;
    private String UserName;
    private Float OrderPrice;
    private Float DeliveryPrice;
    private Float TotalPrice;

    public Orders(Integer id, String userName, Float orderPrice, Float deliveryPrice, Float totalPrice) {
        Id = id;
        UserName = userName;
        OrderPrice = orderPrice;
        DeliveryPrice = deliveryPrice;
        TotalPrice = totalPrice;
    }

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

    public Float getOrderPrice() {
        return OrderPrice;
    }

    public void setOrderPrice(Float orderPrice) {
        OrderPrice = orderPrice;
    }

    public Float getDeliveryPrice() {
        return DeliveryPrice;
    }

    public void setDeliveryPrice(Float deliveryPrice) {
        DeliveryPrice = deliveryPrice;
    }

    public Float getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        TotalPrice = totalPrice;
    }
}
