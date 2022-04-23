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

    public void InsertAccount(String username, String password, String phone, String email, String address, byte[] image, String role){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO Account VALUES('"+username+"','"+password+"','"+phone+
                "', '"+email+"', '"+address+"', '"+image+"', '"+role+"')";
        database.execSQL(sql);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
