package hcmute.edu.vn.nhom20.foodie;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    Button btnGetStart;
    public static Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGetStart = (Button) findViewById(R.id.btnGetStart);

        db = new Database(this,"foodie.sqlite",null,1);

        //table Account
        db.QueryData("CREATE TABLE IF NOT EXISTS Account(Username VARCHAR(200) PRIMARY KEY," +
                " Password VARCHAR(200) NOT NULL,Phone VARCHAR(12), Email VARCHAR(200) NOT NULL UNIQUE," +
                "Address NVARCHAR(200), Image BLOB, Role VARCHAR(30) NOT NULL)");

        //table Shop
        db.QueryData("CREATE TABLE IF NOT EXISTS Shop(Name NVARCHAR(200) PRIMARY KEY," +
                " Image BLOB, Address NVARCHAR(200), Phone VARCHAR(12))");

        //table Product
        db.QueryData("CREATE TABLE IF NOT EXISTS Product(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Name NVARCHAR(200) NOT NULL ,Description NVARCHAR(200), Price FLOAT NOT NULL," +
                " Quantity INTEGER NOT NULL, Image BLOB, Category VARCHAR(20), ShopName NVARCHAR(200) REFERENCES Shop(Name))");

        //insert shop

        //db.QueryData("DELETE FROM Shop WHERE Name = 'Uber Eats'");
        //db.InsertShop("Fresh Menu",null," No6, 14th main Road, Indiqube Delta","08040424242");

        //insert product
//        db.QueryData("DELETE FROM Product WHERE Name = 'Tokbokki'");
//        db.QueryData("DELETE FROM Product WHERE Name = 'Soju'");
//        db.InsertProduct("Tokbokki","Simmered rice cake, is a popular Korean food made from small-sized garae-tteok, baked",
//                50000F,20,null,"Food","Tuna Happy");
//        db.InsertProduct("Soju","A distilled spirit from Korea that is traditionally made from rice",
//                22000F,10,null,"Drink","Tuna Happy");

        //insert admin
        db.QueryData("DELETE FROM Account WHERE Username = 'Kabuto'");
        db.InsertAccount("Kabuto","123456",null,"ngobuituananh@gmail.com",null,null,"Admin");
        db.QueryData("DELETE FROM Account WHERE Username = 'Tuan Anh'");
        db.InsertAccount("Tuan Anh","tuananh123",null,"tuananh@gmail.com",null,null,"Customer");

        btnGetStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}