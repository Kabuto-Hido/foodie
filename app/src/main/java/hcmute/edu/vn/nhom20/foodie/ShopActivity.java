package hcmute.edu.vn.nhom20.foodie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity {
    ImageView shopImageDetail,imgAddFavorite,icon_active_home_shopPage,icon_inactive_list_love_shopPage,
            icon_inactive_cart_shopPage,icon_inactive_more_shopPage,btnBackShopPage;
    TextView textviewShopNameDetail,textviewShopAddress,textviewShopPhone,textviewFoodChoose,
            textviewDrinkChoose;
    ListView listviewFoodAnDrink;

    ArrayList<Product> productArrayList;
    FoodOrDrinkAdapter adapter;

    String shopName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        shopImageDetail = (ImageView) findViewById(R.id.shopImageDetail);
        imgAddFavorite = (ImageView) findViewById(R.id.imgAddFavorite);
        icon_active_home_shopPage = (ImageView) findViewById(R.id.icon_active_home_shopPage);
        icon_inactive_list_love_shopPage = (ImageView) findViewById(R.id.icon_inactive_list_love_shopPage);
        icon_inactive_cart_shopPage = (ImageView) findViewById(R.id.icon_inactive_cart_shopPage);
        icon_inactive_more_shopPage = (ImageView) findViewById(R.id.icon_inactive_more_shopPage);
        btnBackShopPage = (ImageView) findViewById(R.id.btnBackShopPage);
        textviewShopNameDetail = (TextView) findViewById(R.id.textviewShopNameDetail);
        textviewShopAddress = (TextView) findViewById(R.id.textviewShopAddress);
        textviewShopPhone = (TextView) findViewById(R.id.textviewShopPhone);
        textviewFoodChoose = (TextView) findViewById(R.id.textviewFoodChoose);
        textviewDrinkChoose = (TextView) findViewById(R.id.textviewDrinkChoose);
        listviewFoodAnDrink = (ListView) findViewById(R.id.listviewFoodAnDrink);

        Intent i = getIntent();
        shopName = i.getStringExtra("shopName");

        Shop shop = MainActivity.db.getAllShopData(shopName);
        textviewShopNameDetail.setText(shopName);
        textviewShopAddress.setText(shop.getAddress());
        textviewShopPhone.setText(shop.getPhone());

        byte[] picture = shop.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(picture,0,picture.length);
        shopImageDetail.setImageBitmap(bitmap);

        productArrayList = new ArrayList<>();

        adapter = new FoodOrDrinkAdapter(ShopActivity.this,R.layout.food_and_drink_row,productArrayList);
        listviewFoodAnDrink.setAdapter(adapter);

        getDataFood();

        textviewFoodChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textviewFoodChoose.setTextColor(Color.parseColor("#FD4D05"));
                textviewDrinkChoose.setTextColor(Color.parseColor("#979797"));
                adapter = new FoodOrDrinkAdapter(ShopActivity.this,R.layout.food_and_drink_row,productArrayList);
                listviewFoodAnDrink.setAdapter(adapter);

                getDataFood();
            }
        });

        textviewDrinkChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textviewFoodChoose.setTextColor(Color.parseColor("#979797"));
                textviewDrinkChoose.setTextColor(Color.parseColor("#FD4D05"));
                adapter = new FoodOrDrinkAdapter(ShopActivity.this,R.layout.food_and_drink_row,productArrayList);
                listviewFoodAnDrink.setAdapter(adapter);

                getDataDrink();
            }
        });

        btnBackShopPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShopActivity.this, HomeActivity.class));
                finish();
            }
        });

        icon_active_home_shopPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShopActivity.this, HomeActivity.class));
                finish();
            }
        });

        icon_inactive_list_love_shopPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShopActivity.this, FavoriteListActivity.class));
                finish();
            }
        });

        icon_inactive_cart_shopPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShopActivity.this, CartActivity.class));
                finish();
            }
        });

        icon_inactive_more_shopPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShopActivity.this, MorePageActivity.class));
                finish();
            }
        });

        listviewFoodAnDrink.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent productDetailIntent = new Intent(ShopActivity.this,ProductDetailActivity.class);
                productDetailIntent.putExtra("idFoodOrDrink",productArrayList.get(i).getId());
                startActivity(productDetailIntent);
                finish();
            }
        });

    }
    private void getDataFood(){
        Cursor FoodData = MainActivity.db.GetData("SELECT * FROM Product WHERE Category = 'Food' " +
                "AND ShopName = '"+ shopName +"'");
        productArrayList.clear();
        while (FoodData.moveToNext()){
            productArrayList.add(new Product(
                    FoodData.getInt(0),
                    FoodData.getString(1),
                    FoodData.getString(2),
                    FoodData.getFloat(3),
                    FoodData.getInt(4),
                    FoodData.getBlob(5),
                    FoodData.getString(6),
                    "Food"));
        }

        adapter.notifyDataSetChanged();
    }

    private void getDataDrink(){
        Cursor DrinkData = MainActivity.db.GetData("SELECT * FROM Product WHERE Category = 'Drink' " +
                "AND ShopName = '"+ shopName +"'");
        productArrayList.clear();
        while (DrinkData.moveToNext()){
            productArrayList.add(new Product(
                    DrinkData.getInt(0),
                    DrinkData.getString(1),
                    DrinkData.getString(2),
                    DrinkData.getFloat(3),
                    DrinkData.getInt(4),
                    DrinkData.getBlob(5),
                    DrinkData.getString(6),
                    "Drink"));
        }

        adapter.notifyDataSetChanged();
    }
}