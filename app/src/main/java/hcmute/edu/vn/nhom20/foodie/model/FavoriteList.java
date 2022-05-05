package hcmute.edu.vn.nhom20.foodie.model;

public class FavoriteList {
    private Integer Id;
    private String UserName;
    private String ShopName;

    public FavoriteList(Integer id, String userName, String shopName) {
        Id = id;
        UserName = userName;
        ShopName = shopName;
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

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }
}
