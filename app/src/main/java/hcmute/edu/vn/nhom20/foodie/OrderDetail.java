package hcmute.edu.vn.nhom20.foodie;

import java.util.PrimitiveIterator;

public class OrderDetail {
    private Integer Id;
    private Integer ProductId;
    private Integer OrderId;
    private Integer Quantity;
    private Float UnitPrice;

    public OrderDetail(Integer id, Integer productId, Integer orderId, Integer quantity, Float unitPrice) {
        Id = id;
        ProductId = productId;
        OrderId = orderId;
        Quantity = quantity;
        UnitPrice = unitPrice;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getProductId() {
        return ProductId;
    }

    public void setProductId(Integer productId) {
        ProductId = productId;
    }

    public Integer getOrderId() {
        return OrderId;
    }

    public void setOrderId(Integer orderId) {
        OrderId = orderId;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }

    public Float getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        UnitPrice = unitPrice;
    }
}
