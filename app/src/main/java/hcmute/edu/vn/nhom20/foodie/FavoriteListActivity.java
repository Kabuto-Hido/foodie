package hcmute.edu.vn.nhom20.foodie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FavoriteListActivity extends AppCompatActivity {
    ImageView imageBackPageFavorite, icon_inactive_home_FavoritePage,
            icon_inactive_cart_FavoritePage, icon_inactive_more_FavoritePage;
    ListView listviewShopFavorite;
    Button btnDeleteShopInFavorite;

    ArrayList<FavoriteList> favoriteArrayList;
    FavoriteAdapter adapter;

    String user;
    int pos=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_list);

        imageBackPageFavorite = (ImageView) findViewById(R.id.imageBackPageFavorite);
        icon_inactive_home_FavoritePage = (ImageView) findViewById(R.id.icon_inactive_home_FavoritePage);
        icon_inactive_cart_FavoritePage = (ImageView) findViewById(R.id.icon_inactive_cart_FavoritePage);
        icon_inactive_more_FavoritePage = (ImageView) findViewById(R.id.icon_inactive_more_FavoritePage);
        listviewShopFavorite = (ListView) findViewById(R.id.listviewShopFavorite);
        btnDeleteShopInFavorite = (Button) findViewById(R.id.btnDeleteShopInFavorite);

        SharedPreferences sharedPrefer = getSharedPreferences("dataLogin", MODE_PRIVATE);
        user = sharedPrefer.getString("userLogin","");

        favoriteArrayList = new ArrayList<>();
        adapter = new FavoriteAdapter(this,R.layout.shop_favorite_row,favoriteArrayList);
        listviewShopFavorite.setAdapter(adapter);

        getDataFavoriteList();

        listviewShopFavorite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pos = i;
            }
        });

        btnDeleteShopInFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FavoriteListActivity.this,"Delete succeed!!",Toast.LENGTH_SHORT).show();
                MainActivity.db.QueryData("DELETE FROM FavoriteList WHERE Id = "+favoriteArrayList.get(pos).getId()+"");
                getDataFavoriteList();
            }
        });

        imageBackPageFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FavoriteListActivity.this, HomeActivity.class));
                finish();
            }
        });

        icon_inactive_home_FavoritePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FavoriteListActivity.this, HomeActivity.class));
                finish();
            }
        });

        icon_inactive_cart_FavoritePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FavoriteListActivity.this, CartActivity.class));
                finish();
            }
        });

        icon_inactive_more_FavoritePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FavoriteListActivity.this, MorePageActivity.class));
                finish();
            }
        });

    }

    private void getDataFavoriteList(){
        Cursor FavoriteData = MainActivity.db.GetData("SELECT * FROM FavoriteList WHERE UserName = '"+user+"'");
        favoriteArrayList.clear();
        while (FavoriteData.moveToNext()){
            favoriteArrayList.add(new FavoriteList(
                    FavoriteData.getInt(0),
                    FavoriteData.getString(1),
                    FavoriteData.getString(2)));
        }

        adapter.notifyDataSetChanged();
    }
}