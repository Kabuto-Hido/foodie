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

import hcmute.edu.vn.nhom20.foodie.adapter.FoodManageAdapter;
import hcmute.edu.vn.nhom20.foodie.model.Product;
import hcmute.edu.vn.nhom20.foodie.model.Shop;

public class FoodManageActivity extends AppCompatActivity {
    ImageView foodManageShopImage, icon_active_home_foodManagePage,
            icon_inactive_more_foodManagePage, btnBackFoodManage;
    TextView textviewShopNameFoodManage;
    Button btnShowFoodListFoodManage, btnShowDrinkListFoodManage, btnAddFood, btnEditFood;
    ListView listviewFoodManage;

    ArrayList<Product> foodList;
    FoodManageAdapter adapter;

    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_manage);

        foodManageShopImage = (ImageView) findViewById(R.id.foodManageShopImage);
        icon_active_home_foodManagePage = (ImageView) findViewById(R.id.icon_active_home_foodManagePage);
        icon_inactive_more_foodManagePage = (ImageView) findViewById(R.id.icon_inactive_more_foodManagePage);
        btnBackFoodManage = (ImageView) findViewById(R.id.btnBackFoodManage);
        textviewShopNameFoodManage = (TextView) findViewById(R.id.textviewShopNameFoodManage);
        btnShowFoodListFoodManage = (Button) findViewById(R.id.btnShowFoodListFoodManage);
        btnShowDrinkListFoodManage = (Button) findViewById(R.id.btnShowDrinkListFoodManage);
        btnAddFood = (Button) findViewById(R.id.btnAddFood);
        btnEditFood = (Button) findViewById(R.id.btnEditFood);
        listviewFoodManage = (ListView) findViewById(R.id.listviewFoodManage);

        SharedPreferences sharedPreferences = getSharedPreferences("dataShop", MODE_PRIVATE);
        name = sharedPreferences.getString("detailShopName","");

        textviewShopNameFoodManage.setText(name);

        Shop shopData = MainActivity.db.getAllShopData(name);
        byte[] picture = shopData.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(picture,0,picture.length);
        foodManageShopImage.setImageBitmap(bitmap);

        foodList = new ArrayList<>();
        adapter = new FoodManageAdapter(FoodManageActivity.this,R.layout.food_manage_row,foodList);
        listviewFoodManage.setAdapter(adapter);

        getFood();

        btnBackFoodManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FoodManageActivity.this, AdminHomeActivity.class));
                finish();
            }
        });

        btnShowDrinkListFoodManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FoodManageActivity.this, DrinkManageActivity.class));
                finish();
            }
        });

        listviewFoodManage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SharedPreferences sharedP = getSharedPreferences("dataFood",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedP.edit();
                editor.putInt("foodId",foodList.get(i).getId());
                editor.commit();
                btnEditFood.setEnabled(true);
            }
        });

        btnAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAddFood = new Intent(FoodManageActivity.this,AddFoodActivity.class);
                intentAddFood.putExtra("shopNameAddFood",name);
                startActivity(intentAddFood);
                finish();
            }
        });

        btnEditFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FoodManageActivity.this,EditFoodActivity.class));
                finish();
            }
        });

        icon_active_home_foodManagePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FoodManageActivity.this,AdminHomeActivity.class));
                finish();
            }
        });

        icon_inactive_more_foodManagePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FoodManageActivity.this,MorePageActivity.class));
                finish();
            }
        });

    }

    private void getFood(){
        Cursor FoodData = MainActivity.db.GetData("SELECT * FROM Product WHERE Category = 'Food' " +
                "AND ShopName = '"+ name +"'");
        foodList.clear();
        while (FoodData.moveToNext()){
            foodList.add(new Product(
                    FoodData.getInt(0),
                    FoodData.getString(1),
                    FoodData.getString(2),
                    FoodData.getFloat(3),
                    FoodData.getInt(4),
                    FoodData.getBlob(5),
                    "Food",
                    name));
        }
        adapter.notifyDataSetChanged();
    }

    public void DialogFoodDelete(int id, String foodName){
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(this);
        dialogDelete.setMessage("Do you want to delete "+foodName+" ?");
        dialogDelete.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.db.QueryData("DELETE FROM Product WHERE Id = "+id+"");
                Toast.makeText(FoodManageActivity.this,"Delete "+foodName,Toast.LENGTH_SHORT).show();
                getFood();
            }
        });

        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogDelete.show();
    }
}