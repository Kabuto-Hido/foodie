package hcmute.edu.vn.nhom20.foodie;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

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
        //return db.
    }

    public void InsertAccount(String username, String password, String phone, String email, String address,
                              byte[] image, String role){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO Account VALUES('"+username+"','"+password+"','"+phone+
                "', '"+email+"', '"+address+"', '"+image+"', '"+role+"')";
        database.execSQL(sql);
    }

    public void InsertShop(String name, byte[] image, String address, String phone){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO Shop VALUES('"+name+"','"+image+"','"+address+
                "', '"+phone+"')";
        database.execSQL(sql);
    }

    public void InsertProduct(String name, String description, Float price, int quantity, byte[] image,
                              String category, String shopname){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO Product VALUES(null,'"+name+"','"+description+"',"+price+
                ", "+quantity+",'"+image+"','"+category+"', '"+shopname+"')";
        database.execSQL(sql);
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

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
