package hcmute.edu.vn.nhom20.foodie;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import hcmute.edu.vn.nhom20.foodie.model.Account;
import hcmute.edu.vn.nhom20.foodie.model.Product;
import hcmute.edu.vn.nhom20.foodie.model.Shop;

public class Database extends SQLiteOpenHelper {

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //Query with no result
    public void QueryData(String sql){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }
    //Query with result
    public Cursor GetData(String sql){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql,null);
    }

    public void InsertAccount(String username, String password, String email, String role){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO Account (Username,Password,Email,Role) VALUES(?,?,?,?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,username);
        statement.bindString(2,password);
        statement.bindString(3,email);
        statement.bindString(4,role);

        statement.executeInsert();
    }

    public void InsertShop(String name, byte[] image, String address, String phone){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO Shop VALUES(?,?,?,?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,name);
        statement.bindBlob(2,image);
        statement.bindString(3,address);
        statement.bindString(4,phone);

        statement.executeInsert();

    }

    public void InsertProduct(String name, String description, Float price, int quantity, byte[] image,
                              String category, String shopname){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO Product VALUES(null,?,?,?,?,?,?,?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,name);
        statement.bindString(2,description);
        statement.bindDouble(3,price);
        statement.bindDouble(4,quantity);
        statement.bindBlob(5,image);
        statement.bindString(6,category);
        statement.bindString(7,shopname);

        statement.executeInsert();
    }

    public void InsertCart(String userName, int idProduct, int quantity){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO Cart VALUES(null,'"+ userName +"', "+ idProduct +", "+ quantity +")";
        database.execSQL(sql);
    }

    public void InsertFavoriteList(String userName, String shopName){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO FavoriteList VALUES(null,'"+ userName +"', '"+ shopName +"')";
        database.execSQL(sql);
    }

    public void InsertOrder(String userName){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO Orders VALUES(null,'"+ userName +"',null,null,null)";
        database.execSQL(sql);
    }

    public void InsertOrderDetail(int productId, int orderId, int quantity, float unitPrice){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO OrderDetail VALUES(null,"+ productId +","+orderId+","+quantity+","+unitPrice+")";
        database.execSQL(sql);
    }

    public void UpdateShop(String name, byte[] image, String address, String phone){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE Shop SET Image = ?, Address = ?, Phone = ? WHERE Name = ?";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindBlob(1,image);
        statement.bindString(2,address);
        statement.bindString(3,phone);
        statement.bindString(4,name);

        statement.executeUpdateDelete();
    }

    public void UpdateProduct(int id, String name, Float price, int quantity, byte[] image){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE Product SET Image = ?, Name = ?, Price = ?, Quantity = ? WHERE Id = ?";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindBlob(1,image);
        statement.bindString(2,name);
        statement.bindDouble(3,price);
        statement.bindDouble(4,quantity);
        statement.bindDouble(5,id);

        statement.executeUpdateDelete();
    }

    public void UpdateAccount(String username, String phone, String email, String address, byte[] image){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE Account SET Phone = ?, Email = ?, Address = ?, Image = ? WHERE Username = ?";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,phone);
        statement.bindString(2,email);
        statement.bindString(3,address);
        statement.bindBlob(4,image);
        statement.bindString(5,username);

        statement.executeUpdateDelete();
    }

    public Shop getAllShopData(String name){
        Shop data = null;
        SQLiteDatabase sqlDB = getReadableDatabase();
        String sql = "SELECT * FROM Shop WHERE Name = '"+ name +"'";
        Cursor cursor = sqlDB.rawQuery(sql,null);
        if (cursor .moveToFirst()) {
            data = new Shop(name, cursor.getBlob(1), cursor.getString(2),cursor.getString(3));
        }
        cursor.close();
        return data;
    }

    public Product getAllProductData(int id){
        Product product = null;
        SQLiteDatabase sqlDB = getReadableDatabase();
        String sql = "SELECT * FROM Product WHERE Id = "+ id +"";
        Cursor data = sqlDB.rawQuery(sql,null);
        if (data .moveToFirst()) {
            product = new Product(id,data.getString(1), data.getString(2),
                    data.getFloat(3),data.getInt(4),data.getBlob(5),data.getString(6),data.getString(7));
        }
        data.close();
        return product;
    }

    public Account getAllAccountData(String userName){
        Account account = null;
        SQLiteDatabase sqlDB = getReadableDatabase();
        String sql = "SELECT * FROM Account WHERE Username = '"+userName+"'";
        Cursor data = sqlDB.rawQuery(sql,null);
        if (data .moveToFirst()) {
            account = new Account(userName, data.getString(1),
                    data.getString(2),data.getString(3),data.getString(4),data.getBlob(5),data.getString(6));
        }
        data.close();
        return account;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
