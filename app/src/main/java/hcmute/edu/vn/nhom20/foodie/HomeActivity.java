package hcmute.edu.vn.nhom20.foodie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import hcmute.edu.vn.nhom20.foodie.adapter.ShopAdapter;
import hcmute.edu.vn.nhom20.foodie.model.Shop;

public class HomeActivity extends AppCompatActivity {
    SearchView searchView;
    TextView textviewPopular, textviewTop;
    GridView gvShop;
    ArrayList<Shop> shopArrayList;
    ShopAdapter adapter;
    ImageView icon_active_home_homepage,icon_inactive_list_love_homepage,icon_inactive_cart_homepage,icon_inactive_more_homepage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        searchView = (SearchView) findViewById(R.id.searchView);;
        textviewPopular = (TextView) findViewById(R.id.textviewPopular);
        textviewTop = (TextView) findViewById(R.id.textviewTop);

        gvShop = (GridView) findViewById(R.id.gvShop);
        shopArrayList = new ArrayList<>();

        icon_active_home_homepage = (ImageView) findViewById(R.id.icon_active_home_homepage);
        icon_inactive_list_love_homepage = (ImageView) findViewById(R.id.icon_inactive_list_love_homepage);
        icon_inactive_cart_homepage = (ImageView) findViewById(R.id.icon_inactive_cart_homepage);
        icon_inactive_more_homepage = (ImageView) findViewById(R.id.icon_inactive_more_homepage);

        adapter = new ShopAdapter(this,R.layout.shop_row,shopArrayList);
        gvShop.setAdapter(adapter);

        Cursor shopData = MainActivity.db.GetData("SELECT * FROM Shop");
        shopArrayList.clear();
        while (shopData.moveToNext()){
            shopArrayList.add(new Shop(
                    shopData.getString(0),
                    shopData.getBlob(1),
                    shopData.getString(2),
                    shopData.getString(3)));
        }
        shopData.close();
        adapter.notifyDataSetChanged();

        gvShop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //startActivity(new Intent(HomeActivity.this, ShopActivity.class));
                Intent intent = new Intent(HomeActivity.this, ShopActivity.class);
                intent.putExtra("shopName",shopArrayList.get(i).getName());
                startActivity(intent);
                finish();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Shop> foundShopList = new ArrayList<Shop>();

                for(Shop s: shopArrayList){
                    if(s.getName().toLowerCase().contains(newText.toLowerCase())){
                        foundShopList.add(s);
                    }
                }

                ShopAdapter shopAdapter = new ShopAdapter(HomeActivity.this,R.layout.shop_row,foundShopList);
                gvShop.setAdapter(shopAdapter);
                return false;
            }
        });

        icon_inactive_list_love_homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, FavoriteListActivity.class));
                finish();
            }
        });

        icon_inactive_cart_homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, CartActivity.class));
                finish();
            }
        });

        icon_inactive_more_homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, MorePageActivity.class));
                finish();
            }
        });
    }

}
