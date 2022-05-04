package hcmute.edu.vn.nhom20.foodie;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DrinkManageActivity extends AppCompatActivity {
    ImageView btnBackDrinkManage,drinkManageShopImage,
            icon_active_home_drinkManagePage, icon_inactive_more_drinkManagePage;
    TextView textviewShopNameDrinkManage;
    Button btnShowFoodListDrinkManage, btnAddDrink, btnEditDrink;
    ListView listviewDrinkManage;

    ArrayList<Product> drinkList;
    DrinkManageAdapter adapter;

    String nameShop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_manage);

        btnBackDrinkManage = (ImageView) findViewById(R.id.btnBackDrinkManage);
        drinkManageShopImage = (ImageView) findViewById(R.id.drinkManageShopImage);
        icon_active_home_drinkManagePage = (ImageView) findViewById(R.id.icon_active_home_drinkManagePage);
        icon_inactive_more_drinkManagePage = (ImageView) findViewById(R.id.icon_inactive_more_drinkManagePage);
        textviewShopNameDrinkManage =(TextView) findViewById(R.id.textviewShopNameDrinkManage);
        btnShowFoodListDrinkManage = (Button) findViewById(R.id.btnShowFoodListDrinkManage);
        btnAddDrink = (Button) findViewById(R.id.btnAddDrink);
        btnEditDrink = (Button) findViewById(R.id.btnEditDrink);
        listviewDrinkManage = (ListView) findViewById(R.id.listviewDrinkManage);

        SharedPreferences sharedPreferences = getSharedPreferences("dataShop", MODE_PRIVATE);
        nameShop = sharedPreferences.getString("detailShopName","");

        textviewShopNameDrinkManage.setText(nameShop);

        Shop shopData = MainActivity.db.getAllShopData(nameShop);
        byte[] picture = shopData.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(picture,0,picture.length);
        drinkManageShopImage.setImageBitmap(bitmap);

        drinkList = new ArrayList<>();
        adapter = new DrinkManageAdapter(DrinkManageActivity.this,R.layout.drink_manage_row,drinkList);
        listviewDrinkManage.setAdapter(adapter);
        getDrink();

        btnBackDrinkManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DrinkManageActivity.this, AdminHomeActivity.class));
                finish();
            }
        });

        btnShowFoodListDrinkManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DrinkManageActivity.this, FoodManageActivity.class));
                finish();
            }
        });

        listviewDrinkManage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SharedPreferences sharedD = getSharedPreferences("dataDrink",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedD.edit();
                editor.putInt("drinkId",drinkList.get(i).getId());
                editor.commit();
                btnEditDrink.setEnabled(true);
            }
        });

        btnAddDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAddDrink = new Intent(DrinkManageActivity.this,AddDrinkActivity.class);
                intentAddDrink.putExtra("shopNameAddDrink",nameShop);
                startActivity(intentAddDrink);
                finish();
            }
        });

        btnEditDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DrinkManageActivity.this,EditDrinkActivity.class));
                finish();
            }
        });

        icon_active_home_drinkManagePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DrinkManageActivity.this,AdminHomeActivity.class));
                finish();
            }
        });

        icon_inactive_more_drinkManagePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DrinkManageActivity.this,MorePageActivity.class));
                finish();
            }
        });

    }

    public void DialogDrinkDelete(int id, String drinkName){
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(this);
        dialogDelete.setMessage("Do you want to delete "+drinkName+" ?");
        dialogDelete.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.db.QueryData("DELETE FROM Product WHERE Id = "+id+"");
                Toast.makeText(DrinkManageActivity.this,"Delete "+drinkName,Toast.LENGTH_SHORT).show();
                getDrink();
            }
        });

        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogDelete.show();
    }

    private void getDrink(){
        Cursor DrinkData = MainActivity.db.GetData("SELECT * FROM Product WHERE Category = 'Drink' " +
                "AND ShopName = '"+ nameShop +"'");
        drinkList.clear();
        while (DrinkData.moveToNext()){
            drinkList.add(new Product(
                    DrinkData.getInt(0),
                    DrinkData.getString(1),
                    DrinkData.getString(2),
                    DrinkData.getFloat(3),
                    DrinkData.getInt(4),
                    DrinkData.getBlob(5),
                    "Drink",
                    nameShop));
        }
        adapter.notifyDataSetChanged();
    }
}