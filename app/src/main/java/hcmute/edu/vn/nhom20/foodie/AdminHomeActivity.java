package hcmute.edu.vn.nhom20.foodie;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AdminHomeActivity extends AppCompatActivity {
    TextView AdminWelcome;
    ListView listviewShop;
    Button btnAddShop,btnDetailShop;
    ArrayList<Shop> shopList;
    AdminShopAdapter adapter;
    ImageView icon_active_home_adminHomepage,icon_inactive_more_adminHomepage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        AdminWelcome = (TextView) findViewById(R.id.AdminWelcome);
        listviewShop = (ListView) findViewById(R.id.listviewShop);
        btnAddShop = (Button) findViewById(R.id.btnAddShop);
        btnDetailShop = (Button) findViewById(R.id.btnDetailShop);
        icon_active_home_adminHomepage = (ImageView) findViewById(R.id.icon_active_home_adminHomepage);
        icon_inactive_more_adminHomepage = (ImageView) findViewById(R.id.icon_inactive_more_adminHomepage);

        shopList = new ArrayList<>();
        adapter = new AdminShopAdapter(this,R.layout.admin_shop_row,shopList);
        listviewShop.setAdapter(adapter);

        getDataShop();

        SharedPreferences sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);
        String value = sharedPreferences.getString("userLogin","");
        AdminWelcome.append(value);

        icon_active_home_adminHomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminHomeActivity.this, AdminHomeActivity.class);
                finish();
                overridePendingTransition(0, 0);
                startActivity(i);
                overridePendingTransition(0, 0);
            }
        });

        icon_inactive_more_adminHomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHomeActivity.this, MorePageActivity.class));
                finish();
            }
        });

        listviewShop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SharedPreferences sharedPref = getSharedPreferences("dataShop",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("detailShopName",shopList.get(i).getName());
                editor.commit();

            }
        });

        btnAddShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminHomeActivity.this, AddShopActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnDetailShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHomeActivity.this, FoodManageActivity.class));
                finish();
            }
        });

    }

    private void getDataShop(){
        Cursor shopData = MainActivity.db.GetData("SELECT * FROM Shop");
        shopList.clear();
        while (shopData.moveToNext()){
            shopList.add(new Shop(
                    shopData.getString(0),
                    shopData.getBlob(1),
                    shopData.getString(2),
                    shopData.getString(3)));
        }

        adapter.notifyDataSetChanged();
    }

    public void DialogDelete(String shopName){
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(this);
        dialogDelete.setMessage("Do you want to delete "+shopName+" ?");
        dialogDelete.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.db.QueryData("DELETE FROM Product WHERE ShopName = '"+ shopName +"'");
                MainActivity.db.QueryData("DELETE FROM Shop WHERE Name = '"+shopName+"'");
                Toast.makeText(AdminHomeActivity.this,"Delete "+shopName,Toast.LENGTH_SHORT).show();
                getDataShop();
            }
        });

        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogDelete.show();
    }

    public void EditShop(String shopName){
        Intent i1 = new Intent(AdminHomeActivity.this,EditShopActivity.class);
        i1.putExtra("editShopName",shopName);
        startActivity(i1);
        finish();
    }
}